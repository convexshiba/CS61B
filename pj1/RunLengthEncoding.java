/* RunLengthEncoding.java */

/**
 * The RunLengthEncoding class defines an object that run-length encodes
 * a PixImage object.  Descriptions of the methods you must implement appear
 * below.  They include constructors of the form
 * <p/>
 * public RunLengthEncoding(int width, int height);
 * public RunLengthEncoding(int width, int height, int[] red, int[] green,
 * int[] blue, int[] runLengths) {
 * public RunLengthEncoding(PixImage image) {
 * <p/>
 * that create a run-length encoding of a PixImage having the specified width
 * and height.
 * <p/>
 * The first constructor creates a run-length encoding of a PixImage in which
 * every pixel is black.  The second constructor creates a run-length encoding
 * for which the runs are provided as parameters.  The third constructor
 * converts a PixImage object into a run-length encoding of that image.
 * <p/>
 * See the README file accompanying this project for additional details.
 */

class DList {
    DListNode head;
    private DListNode sentinel = new DListNode(null, -1);

    public DList() {
        head = sentinel;
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    public void insertFront(Pixel samePixel, int number) {
        DListNode newNode = new DListNode(samePixel, number);
        newNode.next = sentinel.next;
        newNode.prev = sentinel;
        sentinel.next = newNode;
        newNode.next.prev = newNode;
    }

    public void insertEnd(Pixel samePixel, int number) {
        DListNode newNode = new DListNode(samePixel, number);
        newNode.next = sentinel;
        newNode.prev = sentinel.prev;
        sentinel.prev = newNode;
        newNode.prev.next = newNode;
    }
}

class DListNode {
    DListNode next;
    DListNode prev;
    Pixel nodePixel;
    int number;

    public DListNode(Pixel pixel, int number) {
        this.nodePixel = pixel;
        this.number = number;
    }

}

public class RunLengthEncoding implements Iterable {

    /**
     * Define any variables associated with a RunLengthEncoding object here.
     * These variables MUST be private.
     */

    private DList runDList = new DList();
    private int imageWidth;
    private int imageHeight;


    /**
     *  The following methods are required for Part II.
     */

    /**
     * RunLengthEncoding() (with two parameters) constructs a run-length
     * encoding of a black PixImage of the specified width and height, in which
     * every pixel has red, green, and blue intensities of zero.
     *
     * @param width  the width of the image.
     * @param height the height of the image.
     */

    public RunLengthEncoding(int width, int height) {
        imageHeight = height;
        imageWidth = width;
        runDList.insertFront(new Pixel(), width * height);
    }

    /**
     * RunLengthEncoding() (with six parameters) constructs a run-length
     * encoding of a PixImage of the specified width and height.  The runs of
     * the run-length encoding are taken from four input arrays of equal length.
     * Run i has length runLengths[i] and RGB intensities red[i], green[i], and
     * blue[i].
     *
     * @param width      the width of the image.
     * @param height     the height of the image.
     * @param red        is an array that specifies the red intensity of each run.
     * @param green      is an array that specifies the green intensity of each run.
     * @param blue       is an array that specifies the blue intensity of each run.
     * @param runLengths is an array that specifies the length of each run.
     *                   <p/>
     *                   NOTE:  All four input arrays should have the same length (not zero).
     *                   All pixel intensities in the first three arrays should be in the range
     *                   0...255.  The sum of all the elements of the runLengths array should be
     *                   width * height.  (Feel free to quit with an error message if any of these
     *                   conditions are not met--though we won't be testing that.)
     */

    public RunLengthEncoding(int width, int height, int[] red, int[] green,
                             int[] blue, int[] runLengths) {
        imageWidth = width;
        imageHeight = height;

        for (int i = 0; i < red.length; i++) {
            Pixel newPixel = new Pixel(red[i], green[i], blue[i]);
            runDList.insertEnd(newPixel, runLengths[i]);
        }
    }

    /**
     * getWidth() returns the width of the image that this run-length encoding
     * represents.
     *
     * @return the width of the image that this run-length encoding represents.
     */

    public int getWidth() {
        return imageWidth;
    }

    /**
     * getHeight() returns the height of the image that this run-length encoding
     * represents.
     *
     * @return the height of the image that this run-length encoding represents.
     */
    public int getHeight() {
        return imageHeight;
    }

    /**
     * iterator() returns a newly created RunIterator that can iterate through
     * the runs of this RunLengthEncoding.
     *
     * @return a newly created RunIterator object set to the first run of this
     * RunLengthEncoding.
     */
    public RunIterator iterator() {
        return new RunIterator(runDList.head, runDList);
        // You'll want to construct a new RunIterator, but first you'll need to
        // write a constructor in the RunIterator class.
    }

    /**
     * toPixImage() converts a run-length encoding of an image into a PixImage
     * object.
     *
     * @return the PixImage that this RunLengthEncoding encodes.
     */
    public PixImage toPixImage() {
        PixImage runPixImage = new PixImage(imageWidth, imageHeight);
        RunIterator runIterator = iterator();
        int currentPixel = 0;
        while (runIterator.hasNext()) {
            int[] numberAndPixel = runIterator.next();
            for (int i = 1; i <= numberAndPixel[0]; i++) {
                int y = currentPixel / imageWidth;
                int x = currentPixel - y * imageWidth;
                runPixImage.setPixel(x, y, (short) numberAndPixel[1], (short) numberAndPixel[2], (short) numberAndPixel[3]);
                currentPixel++;
            }
        }

        return runPixImage;
    }

    /**
     * toString() returns a String representation of this RunLengthEncoding.
     * <p/>
     * This method isn't required, but it should be very useful to you when
     * you're debugging your code.  It's up to you how you represent
     * a RunLengthEncoding as a String.
     *
     * @return a String representation of this RunLengthEncoding.
     */
    public String toString() {
        RunIterator runIterator = iterator();
        String runString = "";

        while (runIterator.hasNext()) {
            int[] numberAndPixel = runIterator.next();
            runString += "[" + numberAndPixel[0] + ", {" + numberAndPixel[1] + ", " + numberAndPixel[2] + ", " + numberAndPixel[3] + "}] ";
        }
        return runString;
    }


    /**
     *  The following methods are required for Part III.
     */

    /**
     * RunLengthEncoding() (with one parameter) is a constructor that creates
     * a run-length encoding of a specified PixImage.
     * <p/>
     * Note that you must encode the image in row-major format, i.e., the second
     * pixel should be (1, 0) and not (0, 1).
     *
     * @param image is the PixImage to run-length encode.
     */
    public RunLengthEncoding(PixImage image) {
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                Pixel currentImagePixel = image.getPixel(x, y);
                if (!currentImagePixel.equals(runDList.head.prev.nodePixel)) {
                    runDList.insertEnd(currentImagePixel, 1);
                } else {
                    runDList.head.prev.number++;
                }
            }
        }
        check();
    }

    /**
     * check() walks through the run-length encoding and prints an error message
     * if two consecutive runs have the same RGB intensities, or if the sum of
     * all run lengths does not equal the number of pixels in the image.
     */
    public void check() {
        DListNode current = runDList.head.next;
        int number = 0;
        while (current.nodePixel != null) {
            if (current.nodePixel.equals(current.next.nodePixel)) {
                System.out.println("Constructor Error");
//                Thread.dumpStack();
                return;
            } else {
                number += current.number;
                current = current.next;
            }
        }
        if (number != imageHeight * imageWidth) {
            System.out.println("Wrong Size");
            System.out.println(this);
            System.out.println(number + "!=" + imageHeight * imageWidth);
            Thread.dumpStack();
            System.exit(0);
        }
//        System.out.println("Checked Out! And Same Size!");
    }


    /**
     *  The following method is required for Part IV.
     */

    /**
     * setPixel() modifies this run-length encoding so that the specified color
     * is stored at the given (x, y) coordinates.  The old pixel value at that
     * coordinate should be overwritten and all others should remain the same.
     * The updated run-length encoding should be compressed as much as possible;
     * there should not be two consecutive runs with exactly the same RGB color.
     *
     * @param x     the x-coordinate of the pixel to modify.
     * @param y     the y-coordinate of the pixel to modify.
     * @param red   the new red intensity to store at coordinate (x, y).
     * @param green the new green intensity to store at coordinate (x, y).
     * @param blue  the new blue intensity to store at coordinate (x, y).
     */
    public void setPixel(int x, int y, short red, short green, short blue) {
        // Your solution here, but you should probably leave the following line
        //   at the end.
        if (x < 0 || x >= imageWidth || y < 0 || y > imageHeight) {
            System.out.println("Out of Index");
        }

        DListNode currentRun = runDList.head.next;

        int targetIndex = y * imageWidth + x;
        int currentIndex = currentRun.number - 1;

        while (currentIndex < targetIndex) {
            currentRun = currentRun.next;
            currentIndex += currentRun.number;

        }


        Pixel targetPixel = new Pixel(red, green, blue);
        if (currentRun.nodePixel.equals(targetPixel)) {
//            System.out.println(""+currentRun.nodePixel+"  "+ targetIndex + " " + currentIndex);
//            System.out.println("the same");
            return;
        } else {
//            System.out.println(""+currentRun.nodePixel+"  "+ targetIndex + " " + currentIndex);
            setRunPixel(currentRun, targetPixel, targetIndex, currentIndex);
        }
        check();
    }

    private void setRunPixel(DListNode node, Pixel targetPixel, int targetIndex, int currentIndex) {
        if (node.number == 1) {
            node.nodePixel = targetPixel;
            checkRun();
        } else {
            DListNode newNode1 = new DListNode(node.nodePixel, targetIndex - currentIndex + node.number - 1);
//            System.out.println(newNode1.number+""+newNode1.nodePixel);
            DListNode newNode2 = new DListNode(targetPixel, 1);
//            System.out.println(newNode2.number+""+newNode2.nodePixel);
            DListNode newNode3 = new DListNode(node.nodePixel, currentIndex - targetIndex);
//            System.out.println(newNode3.number+""+newNode3.nodePixel);


            node.prev.next = newNode1;
            newNode1.prev = node.prev;

            newNode1.next = newNode2;
            newNode2.prev = newNode1;

            newNode2.next = newNode3;
            newNode3.prev = newNode2;

            newNode3.next = node.next;
            node.next.prev = newNode3;

            checkRun();
        }
    }

    private void checkNode(DListNode node) {
        if (node.number == 0) {
            //            System.out.println("delete"+node.nodePixel);
            node.prev.next = node.next;
            node.next.prev = node.prev;
            if (node.next.nodePixel != null) {
                checkNode(node.next);
            }
        } else {
            if (node.prev.nodePixel != null && node.nodePixel.equals(node.prev.nodePixel)) {
                System.out.println("merge" + node.nodePixel);
                node.prev.number += node.number;
                System.out.println("node.prev.number" + node.prev.number + node.nodePixel);
                node.prev.next = node.next;
                node.next.prev = node.prev;
                if (node.next.nodePixel != null) {
                    checkNode(node.next);
                }
            }
        }
    }

    private void checkRun() {
        DListNode current = runDList.head.next;
        while (current.nodePixel != null) {
            if (current.number == 0) {
                current.prev.next = current.next;
                current.next.prev = current.prev;

            } else {
                if (current.nodePixel.equals(current.prev.nodePixel)) {
                    current.prev.number += current.number;
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
            }
            current = current.next;
        }
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
     * setAndCheckRLE() sets the given coordinate in the given run-length
     * encoding to the given value and then checks whether the resulting
     * run-length encoding is correct.
     *
     * @param rle       the run-length encoding to modify.
     * @param x         the x-coordinate to set.
     * @param y         the y-coordinate to set.
     * @param intensity the grayscale intensity to assign to pixel (x, y).
     */
    private static void setAndCheckRLE(RunLengthEncoding rle,
                                       int x, int y, int intensity) {
        rle.setPixel(x, y,
                (short) intensity, (short) intensity, (short) intensity);
        rle.check();
    }

    /**
     * main() runs a series of tests of the run-length encoding code.
     */
    public static void main(String[] args) {
        // Be forwarned that when you write arrays directly in Java as below,
        // each "row" of text is a column of your image--the numbers get
        // transposed.


        PixImage image1 = array2PixImage(new int[][]{{0, 3, 6},
                {1, 4, 7},
                {2, 5, 8}});

        System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                "on a 3x3 image.  Input image:");
        System.out.print(image1);
        RunLengthEncoding rle1 = new RunLengthEncoding(image1);
        rle1.check();
        System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
        doTest(rle1.getWidth() == 3 && rle1.getHeight() == 3,
                "RLE1 has wrong dimensions");


        System.out.println(rle1.toPixImage());

        System.out.println("Testing toPixImage() on a 3x3 encoding.");
        doTest(image1.equals(rle1.toPixImage()),
                "image1 -> RLE1 -> image does not reconstruct the original image");


        System.out.println("Testing setPixel() on a 3x3 encoding.");

        setAndCheckRLE(rle1, 0, 0, 42);


        image1.setPixel(0, 0, (short) 42, (short) 42, (short) 42);


        doTest(rle1.toPixImage().equals(image1),
           /*
                       array2PixImage(new int[][] { { 42, 3, 6 },
                                                    { 1, 4, 7 },
                                                    { 2, 5, 8 } })),
           */
                "Setting RLE1[0][0] = 42 fails.");


        System.out.println("Testing setPixel() on a 3x3 encoding.");
        setAndCheckRLE(rle1, 1, 0, 42);
        image1.setPixel(1, 0, (short) 42, (short) 42, (short) 42);
        doTest(rle1.toPixImage().equals(image1),
                "Setting RLE1[1][0] = 42 fails.");

        System.out.println("Testing setPixel() on a 3x3 encoding.");
        setAndCheckRLE(rle1, 0, 1, 2);
        image1.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
        doTest(rle1.toPixImage().equals(image1),
                "Setting RLE1[0][1] = 2 fails.");

        System.out.println("Testing setPixel() on a 3x3 encoding.");

        setAndCheckRLE(rle1, 0, 0, 0);
        image1.setPixel(0, 0, (short) 0, (short) 0, (short) 0);
        doTest(rle1.toPixImage().equals(image1),
                "Setting RLE1[0][0] = 0 fails.");

        System.out.println("Testing setPixel() on a 3x3 encoding.");
        setAndCheckRLE(rle1, 2, 2, 7);
        image1.setPixel(2, 2, (short) 7, (short) 7, (short) 7);
        doTest(rle1.toPixImage().equals(image1),
                "Setting RLE1[2][2] = 7 fails.");
        System.out.println(rle1);

        System.out.println("Testing setPixel() on a 3x3 encoding.");
        setAndCheckRLE(rle1, 2, 2, 42);
        image1.setPixel(2, 2, (short) 42, (short) 42, (short) 42);
        doTest(rle1.toPixImage().equals(image1),
                "Setting RLE1[2][2] = 42 fails.");


        System.out.println("Testing setPixel() on a 3x3 encoding.");
        setAndCheckRLE(rle1, 1, 2, 42);
        image1.setPixel(1, 2, (short) 42, (short) 42, (short) 42);
        doTest(rle1.toPixImage().equals(image1),
                "Setting RLE1[1][2] = 42 fails.");


        PixImage image2 = array2PixImage(new int[][]{{2, 3, 5},
                {2, 4, 5},
                {3, 4, 6}});

        System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                "on another 3x3 image.  Input image:");
        System.out.print(image2);
        RunLengthEncoding rle2 = new RunLengthEncoding(image2);
        rle2.check();
        System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
        doTest(rle2.getWidth() == 3 && rle2.getHeight() == 3,
                "RLE2 has wrong dimensions");

        System.out.println("Testing toPixImage() on a 3x3 encoding.");
        doTest(rle2.toPixImage().equals(image2),
                "image2 -> RLE2 -> image does not reconstruct the original image");

        System.out.println("Testing setPixel() on a 3x3 encoding.");
        setAndCheckRLE(rle2, 0, 1, 2);
        image2.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
        doTest(rle2.toPixImage().equals(image2),
                "Setting RLE2[0][1] = 2 fails.");

        System.out.println("Testing setPixel() on a 3x3 encoding.");
        setAndCheckRLE(rle2, 2, 0, 2);
        image2.setPixel(2, 0, (short) 2, (short) 2, (short) 2);
        doTest(rle2.toPixImage().equals(image2),
                "Setting RLE2[2][0] = 2 fails.");


        PixImage image3 = array2PixImage(new int[][]{{0, 5},
                {1, 6},
                {2, 7},
                {3, 8},
                {4, 9}});

        System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                "on a 5x2 image.  Input image:");
        System.out.print(image3);
        RunLengthEncoding rle3 = new RunLengthEncoding(image3);
        rle3.check();
        System.out.println("Testing getWidth/getHeight on a 5x2 encoding.");
        doTest(rle3.getWidth() == 5 && rle3.getHeight() == 2,
                "RLE3 has wrong dimensions");

        System.out.println("Testing toPixImage() on a 5x2 encoding.");
        doTest(rle3.toPixImage().equals(image3),
                "image3 -> RLE3 -> image does not reconstruct the original image");

        System.out.println("Testing setPixel() on a 5x2 encoding.");
        setAndCheckRLE(rle3, 4, 0, 6);
        image3.setPixel(4, 0, (short) 6, (short) 6, (short) 6);
        doTest(rle3.toPixImage().equals(image3),
                "Setting RLE3[4][0] = 6 fails.");

        System.out.println("Testing setPixel() on a 5x2 encoding.");
        setAndCheckRLE(rle3, 0, 1, 6);
        image3.setPixel(0, 1, (short) 6, (short) 6, (short) 6);
        doTest(rle3.toPixImage().equals(image3),
                "Setting RLE3[0][1] = 6 fails.");

        System.out.println("Testing setPixel() on a 5x2 encoding.");
        setAndCheckRLE(rle3, 0, 0, 1);
        image3.setPixel(0, 0, (short) 1, (short) 1, (short) 1);
        doTest(rle3.toPixImage().equals(image3),
                "Setting RLE3[0][0] = 1 fails.");


        PixImage image4 = array2PixImage(new int[][]{{0, 3},
                {1, 4},
                {2, 5}});

        System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                "on a 3x2 image.  Input image:");
        System.out.print(image4);
        RunLengthEncoding rle4 = new RunLengthEncoding(image4);
        rle4.check();
        System.out.println("Testing getWidth/getHeight on a 3x2 encoding.");
        doTest(rle4.getWidth() == 3 && rle4.getHeight() == 2,
                "RLE4 has wrong dimensions");

        System.out.println("Testing toPixImage() on a 3x2 encoding.");
        doTest(rle4.toPixImage().equals(image4),
                "image4 -> RLE4 -> image does not reconstruct the original image");

        System.out.println("Testing setPixel() on a 3x2 encoding.");
        setAndCheckRLE(rle4, 2, 0, 0);
        image4.setPixel(2, 0, (short) 0, (short) 0, (short) 0);
        doTest(rle4.toPixImage().equals(image4),
                "Setting RLE4[2][0] = 0 fails.");

        System.out.println("Testing setPixel() on a 3x2 encoding.");
        setAndCheckRLE(rle4, 1, 0, 0);
        image4.setPixel(1, 0, (short) 0, (short) 0, (short) 0);
        doTest(rle4.toPixImage().equals(image4),
                "Setting RLE4[1][0] = 0 fails.");

        System.out.println("Testing setPixel() on a 3x2 encoding.");
        setAndCheckRLE(rle4, 1, 0, 1);
        image4.setPixel(1, 0, (short) 1, (short) 1, (short) 1);
        doTest(rle4.toPixImage().equals(image4),
                "Setting RLE4[1][0] = 1 fails.");
    }
}
