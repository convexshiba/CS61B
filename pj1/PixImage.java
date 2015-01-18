/* PixImage.java */

package pj1;

/**
 * The PixImage class represents an image, which is a rectangular grid of
 * color pixels.  Each pixel has red, green, and blue intensities in the range
 * 0...255.  Descriptions of the methods you must implement appear below.
 * They include a constructor of the form
 * <p/>
 * public PixImage(int width, int height);
 * <p/>
 * that creates a black (zero intensity) image of the specified width and
 * height.  Pixels are numbered in the range (0...width - 1, 0...height - 1).
 * <p/>
 * All methods in this class must be implemented to complete Part I.
 * See the README file accompanying this project for additional details.
 */

class Pixel {
    short R;
    short G;
    short B;

    Pixel(int R, int G, int B) {
        this.R = (short) R;
        this.G = (short) G;
        this.B = (short) B;
    }

    Pixel() {
        R = B = G = (short) 0;
    }

    public String toString() {
        return "[" + R + ", " + G + ", " + B + "]";
    }

    public short getRGB(int i) {
        if (i < 0 || i > 2) {
            System.out.println("RBG index out of bound");
        }

        switch (i) {
            case 0:
                return R;
            case 1:
                return G;
            default:
                return B;
        }
    }

    public void setRGB(short foo) {
        R = G = B = foo;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pixel)) {
            return false;
        } else {
            if (((Pixel) o).R == R && ((Pixel) o).G == G && ((Pixel) o).B == B) {
                return true;
            } else {
                return false;
            }
        }
    }
}


public class PixImage {
    /**
     * Define any variables associated with a PixImage object here.  These
     * variables MUST be private.
     */
    private int size;
    private int width;
    private int height;
    private Pixel[][] pixels;

    /**
     * PixImage() constructs an empty PixImage with a specified width and height.
     * Every pixel has red, green, and blue intensities of zero (solid black).
     *
     * @param width  the width of the image.
     * @param height the height of the image.
     */
    public PixImage(int width, int height) {
        this.width = width;
        this.height = height;
        size = width * height;

        pixels = new Pixel[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[y][x] = new Pixel(0, 0, 0);
            }
        }
    }

    public PixImage(PixImage image) {
        size = image.size;
        width = image.width;
        height = image.height;
        pixels = image.pixels.clone();
    }

    private Pixel zeroGetPixel(int widthIndex, int heightIndex) {
        if (widthIndex < 0 || widthIndex >= width) {
            return new Pixel();
        }

        if (heightIndex < 0 || heightIndex >= height) {
            return new Pixel();
        }
        return pixels[heightIndex][widthIndex];
    }

    private Pixel mirrorGetPixel(int heightIndex, int widthIndex) {
        if (widthIndex < 0) {
            widthIndex = -widthIndex - 1;
        } else if (widthIndex >= width) {
            widthIndex = 2 * width - 1 - widthIndex;
        }
        if (heightIndex < 0) {
            heightIndex = -heightIndex - 1;
        } else if (heightIndex >= height) {
            heightIndex = 2 * height - 1 - heightIndex;
        }
        return pixels[heightIndex][widthIndex];
    }

    public Pixel getPixel(int x, int y) {
        return pixels[y][x];
    }

    /**
     * getWidth() returns the width of the image.
     *
     * @return the width of the image.
     */
    public int getWidth() {
        return width;
    }

    /**
     * getHeight() returns the height of the image.
     *
     * @return the height of the image.
     */
    public int getHeight() {
        return height;
    }

    /**
     * getRed() returns the red intensity of the pixel at coordinate (x, y).
     *
     * @param x the x-coordinate of the pixel.
     * @param y the y-coordinate of the pixel.
     * @return the red intensity of the pixel at coordinate (x, y).
     */
    public short getRed(int x, int y) {
        return pixels[y][x].R;
    }

    /**
     * getGreen() returns the green intensity of the pixel at coordinate (x, y).
     *
     * @param x the x-coordinate of the pixel.
     * @param y the y-coordinate of the pixel.
     * @return the green intensity of the pixel at coordinate (x, y).
     */
    public short getGreen(int x, int y) {
        return pixels[y][x].G;
    }

    /**
     * getBlue() returns the blue intensity of the pixel at coordinate (x, y).
     *
     * @param x the x-coordinate of the pixel.
     * @param y the y-coordinate of the pixel.
     * @return the blue intensity of the pixel at coordinate (x, y).
     */
    public short getBlue(int x, int y) {
        return pixels[y][x].B;
    }

    /**
     * setPixel() sets the pixel at coordinate (x, y) to specified red, green,
     * and blue intensities.
     * <p/>
     * If any of the three color intensities is NOT in the range 0...255, then
     * this method does NOT change any of the pixel intensities.
     *
     * @param x     the x-coordinate of the pixel.
     * @param y     the y-coordinate of the pixel.
     * @param red   the new red intensity for the pixel at coordinate (x, y).
     * @param green the new green intensity for the pixel at coordinate (x, y).
     * @param blue  the new blue intensity for the pixel at coordinate (x, y).
     */
    public void setPixel(int x, int y, short red, short green, short blue) {
        if (x >= width || y >= height || x < 0 || y < 0) {
            System.out.println("setPixel Nothing1");
            return;
        }
        if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
            System.out.println("setPixel Nothing2");
            return;
        }
        pixels[y][x] = new Pixel(red, green, blue);
    }

    /**
     * toString() returns a String representation of this PixImage.
     * <p/>
     * This method isn't required, but it should be very useful to you when
     * you're debugging your code.  It's up to you how you represent a PixImage
     * as a String.
     *
     * @return a String representation of this PixImage.
     */
    public String toString() {
        String Pixels = "";
        for (Pixel[] row : pixels) {
            for (Pixel pixel : row) {
                Pixels += pixel + " ";
            }
            Pixels += "%n";
        }
        Pixels = String.format(Pixels);
        return Pixels;
    }

    /**
     * boxBlur() returns a blurred version of "this" PixImage.
     * <p/>
     * If numIterations == 1, each pixel in the output PixImage is assigned
     * a value equal to the average of its neighboring pixels in "this" PixImage,
     * INCLUDING the pixel itself.
     * <p/>
     * A pixel not on the image boundary has nine neighbors--the pixel itself and
     * the eight pixels surrounding it.  A pixel on the boundary has six
     * neighbors if it is not a corner pixel; only four neighbors if it is
     * a corner pixel.  The average of the neighbors is the sum of all the
     * neighbor pixel values (including the pixel itself) divided by the number
     * of neighbors, with non-integer quotients rounded toward zero (as Java does
     * naturally when you divide two integers).
     * <p/>
     * Each color (red, green, blue) is blurred separately.  The red input should
     * have NO effect on the green or blue outputs, etc.
     * <p/>
     * The parameter numIterations specifies a number of repeated iterations of
     * box blurring to perform.  If numIterations is zero or negative, "this"
     * PixImage is returned (not a copy).  If numIterations is positive, the
     * return value is a newly constructed PixImage.
     * <p/>
     * IMPORTANT:  DO NOT CHANGE "this" PixImage!!!  All blurring/changes should
     * appear in the new, output PixImage only.
     *
     * @param numIterations the number of iterations of box blurring.
     * @return a blurred version of "this" PixImage.
     */
    public PixImage boxBlur(int numIterations) {
        if (numIterations <= 0) {
            return this;
        }

        PixImage product = new PixImage(this);

        while (numIterations > 0) {
            product = blurImage(product);
            numIterations--;
        }

        return product;
    }

    private PixImage blurImage(PixImage image) {
        PixImage blurredImage = new PixImage(width, height);

        for (int x = 0; x < image.width; x++) {
            for (int y = 0; y < image.height; y++) {
                blurredImage.pixels[y][x] = blurPixel(x, y, image);
            }
        }
        return blurredImage;
    }

    private Pixel blurPixel(int x, int y, PixImage image) {
        int RSum = 0;
        int BSum = 0;
        int GSum = 0;

        for (int xShift = -1; xShift <= 1; xShift++) {
            for (int yShift = -1; yShift <= 1; yShift++) {
                RSum += image.zeroGetPixel(x + xShift, y + yShift).R;
                GSum += image.zeroGetPixel(x + xShift, y + yShift).G;
                BSum += image.zeroGetPixel(x + xShift, y + yShift).B;
            }
        }

        int position = 0;

        if (x == 0 || x == width - 1) {
            position++;
        }

        if (y == 0 || y == height - 1) {
            position++;
        }

        switch (position) {
            case 0:
                RSum /= 9;
                BSum /= 9;
                GSum /= 9;
                break;
            case 1:
                RSum /= 6;
                BSum /= 6;
                GSum /= 6;
                break;
            case 2:
                RSum /= 4;
                GSum /= 4;
                BSum /= 4;
                break;
        }

        Pixel blurredPixel = new Pixel();
        blurredPixel.R = (short) RSum;
        blurredPixel.B = (short) BSum;
        blurredPixel.G = (short) GSum;

        return blurredPixel;
    }

    /**
     * mag2gray() maps an energy (squared vector magnitude) in the range
     * 0...24,969,600 to a grayscale intensity in the range 0...255.  The map
     * is logarithmic, but shifted so that values of 5,080 and below map to zero.
     * <p/>
     * DO NOT CHANGE THIS METHOD.  If you do, you will not be able to get the
     * correct images and pass the autograder.
     *
     * @param mag the energy (squared vector magnitude) of the pixel whose
     *            intensity we want to compute.
     * @return the intensity of the output pixel.
     */
    private static short mag2gray(long mag) {
        short intensity = (short) (30.0 * Math.log(1.0 + (double) mag) - 256.0);

        // Make sure the returned intensity is in the range 0...255, regardless of
        // the input value.
        if (intensity < 0) {
            intensity = 0;
        } else if (intensity > 255) {
            intensity = 255;
        }
        return intensity;
    }

    /**
     * sobelEdges() applies the Sobel operator, identifying edges in "this"
     * image.  The Sobel operator computes a magnitude that represents how
     * strong the edge is.  We compute separate gradients for the red, blue, and
     * green components at each pixel, then sum the squares of the three
     * gradients at each pixel.  We convert the squared magnitude at each pixel
     * into a grayscale pixel intensity in the range 0...255 with the logarithmic
     * mapping encoded in mag2gray().  The output is a grayscale PixImage whose
     * pixel intensities reflect the strength of the edges.
     * <p/>
     * See http://en.wikipedia.org/wiki/Sobel_operator#Formulation for details.
     *
     * @return a grayscale PixImage representing the edges of the input image.
     * Whiter pixels represent stronger edges.
     */
    public PixImage sobelEdges() {
        PixImage edgedImage = new PixImage(width, height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                edgedImage.pixels[y][x].setRGB(mag2gray(getEnergy(x, y)));
            }
        }

        return edgedImage;
        // Don't forget to use the method mag2gray() above to convert energies to
        // pixel intensities.
    }

    private long getEnergy(int x, int y) {
        long[][] gradients = getGradient(x, y);
        long sum = 0L;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                sum += gradients[i][j] * gradients[i][j];
            }
        }
        return sum;

    }

    private long[][] getGradient(int x, int y) {
        long[][] gradientRGB = new long[3][2];

        for (int i = 0; i < 3; i++) {
            gradientRGB[i][0] += mirrorGetPixel(y - 1, x - 1).getRGB(i) - mirrorGetPixel(y - 1, x + 1).getRGB(i) +
                    2 * mirrorGetPixel(y, x - 1).getRGB(i) - 2 * mirrorGetPixel(y, x + 1).getRGB(i) +
                    mirrorGetPixel(y + 1, x - 1).getRGB(i) - mirrorGetPixel(y + 1, x + 1).getRGB(i);


            gradientRGB[i][1] += mirrorGetPixel(y - 1, x - 1).getRGB(i) - mirrorGetPixel(y + 1, x - 1).getRGB(i) +
                    2 * mirrorGetPixel(y - 1, x).getRGB(i) - 2 * mirrorGetPixel(y + 1, x).getRGB(i) +
                    mirrorGetPixel(y - 1, x + 1).getRGB(i) - mirrorGetPixel(y + 1, x + 1).getRGB(i);

        }
        return gradientRGB;
    }


    /**
     * TEST CODE:  YOU DO NOT NEED TO FILL IN ANY METHODS BELOW THIS POINT.
     * You are welcome to add tests, though.  Methods below this point will not
     * be tested.  This is not the autograder, which will be provided separately.
     */


    /**
     * doTest() checks whether the condition is true and prints the given error
     * message if it is not.
     *
     * @param b   the condition to check.
     * @param msg the error message to print if the condition is false.
     */
    private static void doTest(boolean b, String msg) {
        if (b) {
            System.out.println("Good.");
        } else {
            System.err.println(msg);
        }
    }

    /**
     * array2PixImage() converts a 2D array of grayscale intensities to
     * a grayscale PixImage.
     *
     * @param pixels a 2D array of grayscale intensities in the range 0...255.
     * @return a new PixImage whose red, green, and blue values are equal to
     * the input grayscale intensities.
     */
    private static PixImage array2PixImage(int[][] pixels) {
        int width = pixels.length;
        int height = pixels[0].length;
        PixImage image = new PixImage(width, height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setPixel(x, y, (short) pixels[x][y], (short) pixels[x][y],
                        (short) pixels[x][y]);
            }
        }

        return image;
    }

    /**
     * equals() checks whether two images are the same, i.e. have the same
     * dimensions and pixels.
     *
     * @param image a PixImage to compare with "this" PixImage.
     * @return true if the specified PixImage is identical to "this" PixImage.
     */
    public boolean equals(PixImage image) {
        int width = getWidth();
        int height = getHeight();

        if (image == null ||
                width != image.getWidth() || height != image.getHeight()) {
            return false;
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (!(getRed(x, y) == image.getRed(x, y) &&
                        getGreen(x, y) == image.getGreen(x, y) &&
                        getBlue(x, y) == image.getBlue(x, y))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * main() runs a series of tests to ensure that the convolutions (box blur
     * and Sobel) are correct.
     */
    public static void main(String[] args) {

        // Be forwarned that when you write arrays directly in Java as below,
        // each "row" of text is a column of your image--the numbers get
        // transposed.
        Pixel newPixel = null;
        Pixel p2 = new Pixel(3, 3, 3);
        System.out.println(p2.equals(newPixel));


//        PixImage image1 = array2PixImage(new int[][]{{0, 10, 240},
//                {30, 120, 250},
//                {80, 250, 255}});
//
//        System.out.println("Testing getWidth/getHeight on a 3x3 image.  " +
//                "Input image:");
//        System.out.print(image1);
//        doTest(image1.getWidth() == 3 && image1.getHeight() == 3,
//                "Incorrect image width and height.");
//
//        System.out.println("Testing blurring on a 3x3 image.");
//        doTest(image1.boxBlur(1).equals(
//                        array2PixImage(new int[][] { { 40, 108, 155 },
//                                { 81, 137, 187 },
//                                { 120, 164, 218 } })),
//                "Incorrect box blur (1 rep):\n" + image1.boxBlur(1));
//
//
//        doTest(image1.boxBlur(2).equals(
//                        array2PixImage(new int[][] { { 91, 118, 146 },
//                                { 108, 134, 161 },
//                                { 125, 151, 176 } })),
//                "Incorrect box blur (2 rep):\n" + image1.boxBlur(2));
//        doTest(image1.boxBlur(2).equals(image1.boxBlur(1).boxBlur(1)),
//                "Incorrect box blur (1 rep + 1 rep):\n" +
//                        image1.boxBlur(2) + image1.boxBlur(1).boxBlur(1));
//
//
//        System.out.println("Testing edge detection on a 3x3 image.");
//
//        doTest(image1.sobelEdges().equals(
//                        array2PixImage(new int[][] { { 104, 189, 180 },
//                                { 160, 193, 157 },
//                                { 166, 178, 96 } })),
//                "Incorrect Sobel:\n" + image1.sobelEdges());
//
//
//        PixImage image2 = array2PixImage(new int[][] { { 0, 100, 100 },
//                { 0, 0, 100 } });
//        System.out.println("Testing getWidth/getHeight on a 2x3 image.  " +
//                "Input image:");
//        System.out.print(image2);
//        doTest(image2.getWidth() == 2 && image2.getHeight() == 3,
//                "Incorrect image width and height.");
//
//        System.out.println("Testing blurring on a 2x3 image.");
//        doTest(image2.boxBlur(1).equals(
//                        array2PixImage(new int[][] { { 25, 50, 75 },
//                                { 25, 50, 75 } })),
//                "Incorrect box blur (1 rep):\n" + image2.boxBlur(1));
//
//        System.out.println("Testing edge detection on a 2x3 image.");
//        doTest(image2.sobelEdges().equals(
//                        array2PixImage(new int[][] { { 122, 143, 74 },
//                                { 74, 143, 122 } })),
//                "Incorrect Sobel:\n" + image2.sobelEdges());
    }
}
