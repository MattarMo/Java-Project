public class MainSim2 {

        //remember need two-dimensional
        private static int[][] prioqarray;
        private static int[] arrElements;

        public static void main(String[] args) {

            testCases();

            for(int i = 0; i < prioqarray.length; i++) {

                int output = priorityQueue(arrElements[i], prioqarray[i]);

                if(output >= 1)
                    System.out.println("Valid");
                else
                    System.out.println("Invalid - fails at index " + output + ".");

            }

        }

        public static void testCases() {

            arrElements = new int[7];
            arrElements[0] = 5;
            arrElements[1] = 5;
            arrElements[2] = 6;
            arrElements[3] = 8;
            arrElements[4] = 7;
            arrElements[5] = (int)Math.pow(2, 10) - 1;
            arrElements[6] = arrElements[5];

            prioqarray = new int[7][];

            prioqarray[0] = new int[]{1, 2, 3, 4, 5};
            prioqarray[1] = new int[]{1, 1, 0, 1, 1};
            prioqarray[2] = new int[]{1, 5, 1, 2, 5, 6};
            prioqarray[3] = new int[]{1, 2, 2, 3, 2, 2, 17, 4};
            prioqarray[4] = new int[]{1, 2, 2, 0, 2, 2, 17, 4};
            prioqarray[5] = tests67();
            prioqarray[6] = tests67();
            prioqarray[6][500] = 1;

        }

        public static int priorityQueue(int size, int[] arr){

            int i = (size - 1) / 2;

            if(i < 0)
                return i;

            int left = 2 * i + 1;
            int right = left + 1;

            if(left < arr.length && (arr[i] > arr[left] || arr[left] == 0)) {
                return left;
            }

            else if(right < arr.length && (arr[i] > arr[right] || arr[right] == 0)) {
                return right;
            }

            else {
                return priorityQueue(size - 1, arr);
            }

        }

        public static int[] tests67() {

            int size = (int)Math.pow(2, 10) - 1;
            int[] array = new int[size];

            int k = 0;
            for(int i = 0; i < 10; i++)
                for(int j = 0; j < (int)Math.pow(2, i); j++) {

                    array[k] = (int)Math.pow(2,i);
                    k += 1;

                }

            return array;

        }

}
