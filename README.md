# Compare Product Prices

A GUI application that allows you to make a comparison of a product's price from selected retailers.

## Getting Started

### Prerequisites

* Download JDK and JRE
* Download Git
* Familiarity with basic UNIX commands

### Installing

* Install Java. Follow the instructions based on your operating system.
* Type the following commands in the terminal (Linux, Mac OS X) or in command prompt (Windows) to check if Java and Git are installed properly:
```
java -version
javac -version
git --version
```
* Clone this repository:
```
git clone <repository-url>
```

### Running the application

* Open the folder with the files and there you will find two directories. The source files are located in 'src' and examples files are in 'product-files'.
* To access the 'src' directory, in the terminal type: 'cd src'
* Run the following commands to compile and launch the application:

```
javac ProductDisplay.java
java ProductDisplay
```

* A GUI window will open. To access an example for preview: Click File -> Open and then find the directory where you saved 'compare-product-prices'. Open it. Select one of the files ending in .txt, which have a product specified with a list of retailers and their prices on the product. The application will display the names of the retailers and the current prices.
* To print a receipt of your order: select the retailer(s) with the price(s) that interest you, click File -> Save.
* There will be an 'orders' directory created in 'compare-product-prices', where you will find a file with your order information. Take that receipt and place your order! Enjoy!
* In the event that 'orders' is not showing, you will have to create one and save your order once again.

## Viewing your product

If you would like to add your product to be displayed in the app with an image, follow these instructions. You can have up to five retailers showing up on the screen. Create a text file(.txt) with the following format:

```
"Name of Product"
"Name of Image with extension"
"Retailer Name" "Original Price" "Sale Price, otherwise place 0.0" "URL of the product page" ***

*** This line can be repeated up to 5 times.
```

Place, both the .txt file and the image of the product in product-files. If there is any difficulty, refer to the example .txt files in 'product-files' as an example.
