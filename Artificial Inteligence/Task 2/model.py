%tensorflow_version 2.x

# TensorFlow and tf.keras
import tensorflow as tf
from tensorflow import keras
import os
from cv2 import cv2
# Helper libraries
import numpy as np
import matplotlib.pyplot as plt


class_names = ['Champion','Item','Passive','Spell']
train_images = []
train_labels = []
test_images = []
test_labels = []

trainingpath = "/content/Training/"
testingpath = "/content/Testing/"

for c in class_names:
  p = os.path.join(trainingpath,c)
  for i in os.listdir(p):
    image = cv2.imread(os.path.join(p,i), cv2.IMREAD_UNCHANGED)
    image = cv2.resize(image, (80, 80))
    train_images.append(image)
    train_labels.append(class_names.index(c))


for c in class_names:
  p = os.path.join(testingpath,c)
  for i in os.listdir(p):
    image = cv2.imread(os.path.join(p,i), cv2.IMREAD_UNCHANGED)
    image = cv2.resize(image, (80, 80))
    test_images.append(image)
    test_labels.append(class_names.index(c))


train_images = np.array(train_images)
train_labels = np.array(train_labels)
test_images = np.array(test_images)
test_labels = np.array(test_labels)


train_images = train_images / 255.0

test_images = test_images / 255.0


train_images = train_images.reshape(train_images.shape[0], train_images.shape[1],train_images.shape[2], 3)

test_images = test_images.reshape(test_images.shape[0], test_images.shape[1],test_images.shape[2], 3)

"""
plt.figure(figsize=(10,10))
for i in range(25):
    plt.subplot(5,5,i+1)
    plt.xticks([])
    plt.yticks([])
    plt.grid(False)
    plt.imshow(train_images[i], cmap=plt.cm.binary)
    plt.xlabel(class_names[train_labels[i]])
plt.show()
"""
## Build the Model ##
#
# Setup the layers
#
model = keras.Sequential([
    keras.layers.Conv2D(32, (3, 3), activation='relu', input_shape=(train_images.shape[1], train_images.shape[2], 3)),
    keras.layers.MaxPooling2D((2,2)),
    keras.layers.Conv2D(64, (3, 3), activation='relu'),
    keras.layers.MaxPooling2D((2,2)),
    keras.layers.Flatten(),
    keras.layers.Dense(64, activation=tf.nn.relu),
    keras.layers.Dense(64, activation=tf.nn.softmax)
])

## Compile the model ##
#

model.compile(optimizer='adam',
              loss='sparse_categorical_crossentropy',
              metrics=['accuracy'])

## Train the model ##
#

model.fit(train_images, train_labels, epochs=8, validation_data = (train_images, train_labels))

## Evaluate accuracy ##
#
test_loss, test_acc = model.evaluate(test_images,  test_labels, verbose=2)

print('Test accuracy:', test_acc)

model.save('/content/drive/My Drive/model.h5')

## Make predictions ##
#
predictions = model.predict(test_images)


predictions[0]


print(class_names[np.argmax(predictions[0])])
