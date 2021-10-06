///////////////////////////////////
// This is the main shift register class.
// Notice that it implements the ILFShiftRegister interface.
// You will need to fill in the functionality.
///////////////////////////////////

/**
 * class ShiftRegister
 * @author Chen Hsiao Ting
 * Description: implements the ILFShiftRegister interface.
 */
public class ShiftRegister implements ILFShiftRegister {
    ///////////////////////////////////
    // Create your class variables here
    ///////////////////////////////////
    private final int size;
    private final int tap;
    private int[] seed;

    ///////////////////////////////////
    // Create your constructor here:
    ///////////////////////////////////
    ShiftRegister(int size, int tap) {
        this.size = size;
        this.tap = tap;
        this.seed = new int[size];
    }
    // initialise all variables to prevent
    // null pointer exception

    ///////////////////////////////////
    // Create your class methods here:
    ///////////////////////////////////
    /**
     * setSeed
     * @param seed
     * Description: check if input is correct. If no, throws an error.
     * If yes, insert parameter values into variable seed.
     */
    @Override
    public void setSeed(int[] seed) {
        for (int i = 0; i < seed.length; i++) {
            if (seed[i] != 0 && seed[i] != 1) {
                System.out.println("Error! Seed contains number other than 0 or 1.");
                break;
            } else {
                this.seed[i] = seed[i];
            }
        }
    }

    /**
     * shift
     * @return bit
     * Description: calculate last bit.
     * Shift every bit in the array to the left.
     */
    @Override
    public int shift() {
        final int lastBit = seed[size-1] ^ seed[tap];
        for (int i = size - 1; i > 0; i--) {
            seed[i] = seed[i-1];
        }
        seed[0] = lastBit;
        return lastBit;
    }

    /**
     * generate
     * @param k
     * @return binary number
     * Description: generate a binary int array.
     */
    @Override
    public int generate(int k) {
        int[] binaryArray = new int[k];

        for (int i = 0; i < k; i++) {
            int bit = this.shift();
            binaryArray[i] = bit;
        }

        return this.toBinary(binaryArray);
    }

    /**
     * Returns the integer representation for a binary int array.
     * @param array
     * @return decimal number
     */
    private int toBinary(int[] array) {
        int decimalNumber = 0;
        for (int i = 0; i < array.length; i++) {
            decimalNumber = decimalNumber * 2 + array[i];
        }
        return decimalNumber;
    }
}
