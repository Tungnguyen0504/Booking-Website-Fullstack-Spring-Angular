import java.util.Arrays;

public class Q1 {
    public int[] twoSum(int[] nums, int target) {
        int[] result = null;
        process: for(int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if(nums[i] + nums[j] == target) {
                    result = new int[]{
                        i, j
                    };
                    break process;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr = new int[] {1, 2, 3, 5, 6};

        System.out.println(Arrays.toString(new Q1().twoSum(arr, 7)));
    }
}
