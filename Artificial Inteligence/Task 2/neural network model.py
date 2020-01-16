from __future__ import absolute_import, division, print_function, unicode_literals

# TensorFlow and tf.keras
import tensorflow as tf
from tensorflow import keras

# Helper libraries
import numpy as np
import matplotlib.pyplot as plt

print(tf.__version__)

# Loading the dataset returns four NumPy arrays:
#
# The train_images and train_labels arrays are the training set—the data the model uses to learn.
# The model is tested against the test set, the test_images, and test_labels arrays.
fashion_mnist = keras.datasets.fashion_mnist

(train_images, train_labels), (test_images, test_labels) = fashion_mnist.load_data()

# Each image is mapped to a single label. Since the class names are not included
# with the dataset, store them here to use later when plotting the images:
class_names = ['T-shirt/top', 'Trouser', 'Pullover', 'Dress', 'Coat',
               'Sandal', 'Shirt', 'Sneaker', 'Bag', 'Ankle boot']

## Explore the data ##
#
# Let's explore the format of the dataset before training the model. The following
# shows there are 60,000 images in the training set, with each image represented
# as 28 x 28 pixels:
train_images.shape

#Likewise, there are 60,000 labels in the training set:
len(train_labels)

# Each label is an integer between 0 and 9:
train_labels

# There are 10,000 images in the test set. Again, each image is represented
# as 28 x 28 pixels:
test_images.shape

# And the test set contains 10,000 images labels:
len(test_labels)

## Preprocess the data ##
#
# The data must be preprocessed before training the network. If you inspect
# the first image in the training set, you will see that the pixel values fall
# in the range of 0 to 255:
plt.figure()
plt.imshow(train_images[0])
plt.colorbar()
plt.grid(False)
plt.show()

# We scale these values to a range of 0 to 1 before feeding to the neural
# network model. For this, we divide the values by 255. It's important that
# the training set and the testing set are preprocessed in the same way:
train_images = train_images / 255.0

test_images = test_images / 255.0


train_images = train_images.reshape(train_images.shape[0], train_images.shape[1],train_images.shape[2], 1)

test_images = test_images.reshape(test_images.shape[0], test_images.shape[1],test_images.shape[2], 1)

# Display the first 25 images from the training set and display the class name
# below each image. Verify that the data is in the correct format and we're ready
# to build and train the network.
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
# The basic building block of a neural network is the layer. Layers extract representations
# from the data fed into them. And, hopefully, these representations are more meaningful
# for the problem at hand.
# Most of deep learning consists of chaining together simple layers. Most layers,
# like tf.keras.layers.Dense, have parameters that are learned during training.
model = keras.Sequential([
    keras.layers.Conv2D(32, (3, 3), activation='relu', input_shape=(train_images.shape[1], train_images.shape[2], 1)),
    keras.layers.MaxPooling2D((2,2)),
    keras.layers.Conv2D(64, (3, 3), activation='relu'),
    keras.layers.MaxPooling2D((2,2)),
    keras.layers.Flatten(),
    keras.layers.Dense(64, activation=tf.nn.relu),
    keras.layers.Dense(64, activation=tf.nn.softmax)
])

## Compile the model ##
#
# Loss function —This measures how accurate the model is during training. We want to
# minimize this function to "steer" the model in the right direction.
#
# Optimizer —This is how the model is updated based on the data it sees and its loss function.
# 
#Metrics —Used to monitor the training and testing steps. The following example uses accuracy,
#the fraction of the images that are correctly classified.
model.compile(optimizer='adam',
              loss='sparse_categorical_crossentropy',
              metrics=['accuracy'])

## Train the model ##
#
# 1. Feed the training data to the model—in this example, the train_images and
# train_labels arrays.
# 2. The model learns to associate images and labels.
# 3. We ask the model to make predictions about a test set—in this example, the test_images
# array. We verify that the predictions match the labels from the test_labels array.

model.fit(train_images, train_labels, epochs=5, validation_data = (train_images, train_labels))

## Evaluate accuracy ##
#
test_loss, test_acc = model.evaluate(test_images,  test_labels, verbose=2)

print('Test accuracy:', test_acc)

## Make predictions ##
#
# With the model trained, we can use it to make predictions about some images.
predictions = model.predict(test_images)

# Here, the model has predicted the label for each image in the testing set. Let's take a look at the first prediction:
predictions[0]

# A prediction is an array of 10 numbers. These describe the "confidence" of the model that
# the image corresponds to each of the 10 different articles of clothing. We can see which
# label has the highest confidence value:
print(class_names[np.argmax(predictions[0])])

# So the model is most confident that this image is an ankle boot, or class_names[9]. And we can check the test label
# to see this is correct:
test_labels[0]

# We can graph this to look at the full set of 10 class predictions

# def plot_image(i, predictions_array, true_label, img):
#   predictions_array, true_label, img = predictions_array, true_label[i], img[i]
#   plt.grid(False)
#   plt.xticks([])
#   plt.yticks([])
  
#   plt.imshow(img, cmap=plt.cm.binary)
  
#   predicted_label = np.argmax(predictions_array)
#   if predicted_label == true_label:
#     color = 'blue'
#   else:
#     color = 'red'
  
#   plt.xlabel("{} {:2.0f}% ({})".format(class_names[predicted_label],
#                                 100*np.max(predictions_array),
#                                 class_names[true_label]),
#                                 color=color)

# def plot_value_array(i, predictions_array, true_label):
#   predictions_array, true_label = predictions_array, true_label[i]
#   plt.grid(False)
#   plt.xticks([])
#   plt.yticks([])
#   thisplot = plt.bar(range(10), predictions_array, color="#777777")
#   plt.ylim([0, 1])
#   predicted_label = np.argmax(predictions_array)
  
#   thisplot[predicted_label].set_color('red')
#   thisplot[true_label].set_color('blue')

# # Let's look at the 0th image, predictions, and prediction array.
# i = 0
# plt.figure(figsize=(6,3))
# plt.subplot(1,2,1)
# plot_image(i, predictions[i], test_labels, test_images)
# plt.subplot(1,2,2)
# plot_value_array(i, predictions[i],  test_labels)
# plt.show()

# i = 12
# plt.figure(figsize=(6,3))
# plt.subplot(1,2,1)
# plot_image(i, predictions[i], test_labels, test_images)
# plt.subplot(1,2,2)
# plot_value_array(i, predictions[i],  test_labels)
# plt.show()

# # Let's plot several images with their predictions. Correct prediction labels
# # are blue and incorrect prediction labels are red. The number gives the percent
# # (out of 100) for the predicted label. Note that it can be wrong even when very
# # confident.
# #
# # Plot the first X test images, their predicted label, and the true label
# # Color correct predictions in blue, incorrect predictions in red
# num_rows = 5
# num_cols = 3
# num_images = num_rows*num_cols
# plt.figure(figsize=(2*2*num_cols, 2*num_rows))
# for i in range(num_images):
#   plt.subplot(num_rows, 2*num_cols, 2*i+1)
#   plot_image(i, predictions[i], test_labels, test_images)
  #plt.subplot(num_rows, 2*num_cols, 2*i+2)
 # plot_value_array(i, predictions[i], test_labels)
#plt.show()

# Finally, use the trained model to make a prediction about a single image.
# Grab an image from the test dataset
#img = test_images[1]

#print(img.shape)

# tf.keras models are optimized to make predictions on a batch, or collection,
# of examples at once. So even though we're using a single image, we need to add
# it to a list:
#
# Add the image to a batch where it's the only member.
#img = (np.expand_dims(img,0))

#print(img.shape)

# Now predict the image:
#predictions_single = model.predict(img)

#print(predictions_single)

#plot_value_array(1, predictions_single[0], test_labels)
#plt.xticks(range(10), class_names, rotation=45)
#plt.show()

# model.predict returns a list of lists, one for each image in the batch of data.
# Grab the predictions for our (only) image in the batch:

#prediction_result = np.argmax(predictions_single[0])
#print(prediction_result)
# And the model predicts a label of 2.
