from flask import Flask, request
from PIL import Image
import numpy as np
import tensorflow as tf
import io

app = Flask(__name__)

model = tf.keras.models.load_model('transfer_learning_model_accurate.h5')


# Preprocess image for model input
def preprocess_image(image_bytes):
    image = Image.open(io.BytesIO(image_bytes))
    image = image.resize((255, 255))
    image = np.array(image) / 255.0
    image = np.expand_dims(image, axis=0)
    return image

#Makes a prediction using the model:
def predict_image(preprocessed_image):
    index = model.predict(preprocessed_image)
    return np.argmax(index)


def index_to_animal(index):
    animals = ['badger', 'bat', 'bee', 'beetle', 'butterfly', 'cat', 'caterpillar', 'cockroach', 'cow', 'crab', 'crow',
               'deer', 'dog', 'donkey', 'dragonfly', 'duck', 'eagle', 'fly', 'fox', 'goat', 'goose', 'grasshopper',
               'hamster', 'hare', 'hedgehog', 'horse', 'ladybugs', 'lobster', 'mosquito', 'moth', 'mouse', 'otter',
               'owl', 'parrot', 'pig', 'pigeon', 'racoon', 'rat', 'sheep', 'snake', 'sparrow', 'squirrel', 'swan',
               'turkey', 'turtle', 'woodpecker']
    return animals[index]


# Define API endpoint for image processing
@app.route('/process_image', methods=['POST'])
def process_image():
    image_bytes = request.data

    prep_image = preprocess_image(image_bytes)
    pred_index = predict_image(prep_image)
    animal = index_to_animal(pred_index)
    print("Predicted Animal:", animal.capitalize())  # for testing
    return animal

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8000)
