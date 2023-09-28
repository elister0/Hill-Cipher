
/**
 * Write a description of class HillCipher here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class HillCipher
{
    int[][] keyMatrix;
     int[][] inverseKeyMatrix;
    private String message;

    public HillCipher(String input, int [][] key)
    {
        message = input.toUpperCase();
        keyMatrix = key;
        inverseKeyMatrix = findInverse(keyMatrix);
    }

    public HillCipher (int n, String input){
        keyMatrix = createKey(n);
        inverseKeyMatrix = findInverse(keyMatrix);
        message = input;
    }

   private int[][] createKey(int n){
        if (n > 3 || n < 2)
        {
            throw new ArithmeticException("The inputted value is not a valid length for the key cipher! Values can only be 2 or 3");
        }
        int [] possibleValues = {1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25};
        int[][] tempMatrix = new int[n][n];
        for (int row = 0; row<n; row++){
            for (int col = 0; col < n; col++){
                tempMatrix[row][col] = possibleValues[(int)(Math.random()*12)];
            }
        }

        if (findDeterminant(tempMatrix) == 0){
            tempMatrix = createKey(n);
        }

        return tempMatrix;
    }

    private int[][] findInverse(int [][] key){
        int[][] inv = new int[key.length][key.length];
        int [][] adjMatrix = findAdjMatrix(key);
        int det = findDeterminant(key);
        int temp = det;
        int invDet = 1;
        while (temp !=1)
        {
            temp -= (26 - det);
            if (temp < 0){
                temp +=26;
            }
            invDet++;
        }

        for (int row = 0; row < key.length; row++){
            for (int col = 0; col < key.length;col++){
                inv[row][col] = (adjMatrix[row][col] * invDet)%26;
            }
        }

        return inv;
    }

    private int findDeterminant (int[][] matrix){
        int det = 0;

        if (matrix.length == 2){
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        } else {
            return ((matrix [0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1])) - (matrix [0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])) + (matrix [0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0])))%26;
        }
    }

    private int[][] findAdjMatrix (int[][] matrix) {
        int[][] adj = new int[matrix.length][matrix.length];
        if (matrix.length == 2) {
            adj[0][0] = matrix[1][1];
            adj[0][1] = -1 * matrix[0][1];
            adj[1][0] = -1 * matrix[1][0];
            adj[1][1] = matrix[0][0];
        } else {
            adj[0][0] = ((matrix[1][1] * matrix[2][2]) - (matrix[1][2] * matrix[2][1]))%26;
            adj[0][1] = ((matrix[2][1] * matrix[0][2]) - (matrix[2][2] * matrix[0][1]))%26;
            adj[0][2] = ((matrix[0][1] * matrix[1][2]) - (matrix[0][2] * matrix[1][1]))%26;
            adj[1][0] = ((matrix[1][2] * matrix[2][0]) - (matrix[1][0] * matrix[2][2]))%26;
            adj[1][1] = ((matrix[2][2] * matrix[0][0]) - (matrix[2][0] * matrix[0][2]))%26;
            adj[1][2] = ((matrix[0][2] * matrix[1][0]) - (matrix[0][0] * matrix[1][2]))%26;
            adj[2][0] = ((matrix[1][0] * matrix[2][1]) - (matrix[1][1] * matrix[2][0]))%26;
            adj[2][1] = ((matrix[2][0] * matrix[0][1]) - (matrix[2][1] * matrix[0][0]))%26;
            adj[2][2] = ((matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]))%26;
        }

        for (int row = 0; row < adj.length; row ++){
            for (int col = 0; col < adj.length; col++){
                if (adj[row][col] < 0){
                    adj[row][col] +=26;
                }
            }
        }
        return adj;
    }


    public String encrypt() {
        String letterMessage = "";
        String encryptedMessage = "";

        //Gets the letters from the message and ignores all other characters such as space or punctuation
        for (int i = 0; i<message.length(); i++){
            if (((int)message.charAt(i) >= 65 && (int)message.charAt(i)<=90)){
                letterMessage += message.charAt(i);
            }
        }

            //creates a new String array that holds the letter message in groups of 3 and adds X when groups are not divisable by 3
            String[] temp = new String[(int) Math.ceil(letterMessage.length() / (double)(keyMatrix.length))];
            int tempIndex = 0;
            for (int i = 1; i < letterMessage.length(); i++) {

                if (i == letterMessage.length() - 1) {
                    if (letterMessage.length() % keyMatrix.length == 1) {
                        temp[tempIndex] = letterMessage.substring(i - keyMatrix.length, i);
                    }
                    if (i % keyMatrix.length == 0) {
                        tempIndex++;
                    }

                    temp[tempIndex] = letterMessage.substring(i - (i % keyMatrix.length), i + 1);

                    for (int j = i % keyMatrix.length; j < keyMatrix.length-1; j++) {
                        temp[tempIndex] += "X";
                    }

                } else if (i % keyMatrix.length == 0) {
                    temp[tempIndex] = letterMessage.substring(i - keyMatrix.length, i);
                    tempIndex++;
                }

            }

            //encrypts the message using the key
            int sum = 0;
            String[] tempEncrypted = new String[temp.length];
            if (keyMatrix.length == 3) {
                for (int i = 0; i < temp.length; i++) {

                    for (int col = 0; col < 3; col++) {

                        sum = ((int) temp[i].charAt(0) - 65) * keyMatrix[0][col] + ((int) temp[i].charAt(1) - 65) * keyMatrix[1][col] + ((int) temp[i].charAt(2) - 65) * keyMatrix[2][col];

                        if (col == 0) {
                            tempEncrypted[i] = Character.toString((int) ((sum % 26) + 65));

                        } else {
                            tempEncrypted[i] += Character.toString((int) ((sum % 26) + 65));

                        }

                    }
                }
            } else {
                for (int i = 0; i< temp.length; i++){
                    for (int col = 0; col < 2; col++){
                        sum = ((int) temp[i].charAt(0) - 65) * keyMatrix[0][col] + ((int) temp[i].charAt(1) - 65) * keyMatrix[1][col];

                        if (col == 0) {
                            tempEncrypted[i] = Character.toString((int) ((sum % 26) + 65));

                        } else {
                            tempEncrypted[i] += Character.toString((int) ((sum % 26) + 65));

                        }
                    }
                }
            }
            //formats encrypted message back into same shape as original including spaces or punctuation
            for (String letters : tempEncrypted) {
                encryptedMessage += letters;
            }
            for (int i = 0; i < message.length(); i++) {
                if (!((int) message.charAt(i) >= 65 && (int) message.charAt(i) <= 90)) {
                    encryptedMessage = encryptedMessage.substring(0, i) + Character.toString(message.charAt(i)) + encryptedMessage.substring(i);
                }
            }


        return encryptedMessage;
    }


    public String decrypt(){
        String encrypted = encrypt();
        String letterMessage = "";
        String decryptedMessage = "";

        //Gets the letters from the message and ignores all other characters such as space or punctuation
        for (int i = 0; i<encrypted.length(); i++){
            if (((int)encrypted.charAt(i) >= 65 && (int)encrypted.charAt(i)<=90)){
                letterMessage += encrypted.charAt(i);
            }
        }

        //creates a new String array that holds the letter message in groups of 3 and adds X when groups are not divisable by 3
        String[] temp = new String[(int)Math.ceil(letterMessage.length()/3.0)];
        int tempIndex = 0;
        for (int i = 1; i < letterMessage.length(); i++) {

            if (i == letterMessage.length() - 1) {
                if (i % 3 == 0) {
                    tempIndex++;
                }

                temp[tempIndex] = letterMessage.substring(i - (i % 3), i + 1);
                for (int j = i % 3; j < 2; j++) {
                    temp[tempIndex] += "X";
                }
            } else if (i % 3 == 0) {
                temp[tempIndex] = letterMessage.substring(i - 3, i);
                tempIndex++;
            }

        }

        //encrypts the message using the key
        int sum = 0;
        String[] tempDecrypted = new String[temp.length];
        for (int i = 0; i<temp.length; i++) {
            for (int col = 0; col<3; col++)
            {

                sum = ((int)temp[i].charAt(0)-65) * inverseKeyMatrix[0][col] + ((int)temp[i].charAt(1)-65) * inverseKeyMatrix[1][col] + ((int)temp[i].charAt(2)-65) * inverseKeyMatrix[2][col];

                if(col == 0){
                    tempDecrypted[i] = Character.toString((int)((sum%26) + 65));

                } else {
                    tempDecrypted[i] += Character.toString((int) ((sum % 26) + 65));

                }

            }
        }

        //formats encrypted message back into same shape as original including spaces or punctuation
        for (String letters: tempDecrypted){
            decryptedMessage += letters;
        }
        for (int i = 0; i < message.length(); i++){
            if (!((int)message.charAt(i) >= 65 && (int)message.charAt(i)<=90)){
                decryptedMessage = decryptedMessage.substring(0,i) + Character.toString(message.charAt(i)) + decryptedMessage.substring(i);
            }
        }
        return decryptedMessage;

    }

    public String matrixToString(int[][] matrix){

        String output = "";
        for (int[] i : matrix){
            for (int j : i){
                output += " " + j;
            }
            output += "\n";
        }
        return output;
    }
}
