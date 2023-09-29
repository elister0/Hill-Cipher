import java.util.*;
/**
 * Write a description of class HillCipherRunner here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class HillCipherRunner
{

    public static void main(String args[])
    {

        Scanner kb = new Scanner(System.in);

        HillCipher userCipher;
        System.out.println("Welcome to Hill Cipher encryption and decryption! Do you have a key for your encryption? (y/n)");
        if (kb.next().toLowerCase().equals("n")){
            System.out.println("No worries! We'll give you one. Would you like the key to be with a matrix size 2 or 3? (2/3)");
            int n = kb.nextInt();
            System.out.println("What message would you like to encrypt/decrypt?");
            kb.next();
            String message = kb.nextLine();

            userCipher = new HillCipher(message, n);

        } else
        {
            System.out.println("Perfect! This program only takes matrix of size 2 or 3. Which size is your key? (2/3)");
            if (kb.nextInt() == 2){
                int[][] temp = new int[2][2];
                for (int row = 0; row< 2; row++){
                    for (int col = 0; col < 2; col++){
                        System.out.println("Please insert an integer of value 1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, or 25");
                        temp[row][col] = kb.nextInt();
                    }
                }
                System.out.println("What message would you like to encrypt/decrypt?");
                userCipher = new HillCipher(kb.nextLine(), temp);



            } else {
                int[][] temp = new int[3][3];
                for (int row = 0; row< 3; row++){
                    for (int col = 0; col < 3; col++){
                        System.out.println("Please insert an integer of value 1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, or 25");
                        temp[row][col] = kb.nextInt();
                    }
                }
                System.out.println("What message would you like to encrypt/decrypt?");
                userCipher = new HillCipher(kb.nextLine(), temp);
            }

        }
        System.out.println("Would you like to encrypt or decrypt? (e/d)");
        if (kb.next().toLowerCase().equals("e")){
            System.out.println("Your encrypted message is : " + userCipher.encrypt());
        } else if (kb.next().toLowerCase().equals("d")){
            System.out.println("Your encrypted message is : " + userCipher.decrypt());
        }


    }


}
