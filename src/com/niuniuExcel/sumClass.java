package com.niuniuExcel;

import java.util.Stack;


public class sumClass {

    /**
     * Set a value for target sum
     */
    public int TARGET_SUM = 0;

    private Stack<item> stack = new Stack<>();

    /**
     * Store the sum of current elements stored in stack
     */
    private int sumInStack = 0;

    public void populateSubset(item[] data, int fromIndex, int endIndex) {

        /*
        * Check if sum of elements stored in Stack is equal to the expected
        * target sum.
        *
        * If so, call print method to print the candidate satisfied result.
        */
        if (sumInStack == TARGET_SUM) {
            print(stack);
            return;
        }

        for (int currentIndex = fromIndex; currentIndex < endIndex; currentIndex++) {

            if (sumInStack + data[currentIndex].getValue() <= TARGET_SUM) {
                stack.push(data[currentIndex]);
                sumInStack += data[currentIndex].getValue();

                /*
                * Make the currentIndex +1, and then use recursion to proceed
                * further.
                */
                populateSubset(data, currentIndex + 1, endIndex);
                sumInStack -= (Integer) stack.pop().getValue();
            }
        }
    }

    /**
     * Print satisfied result. i.e. 15 = 4+6+5
     */

    private void print(Stack<item> stack) {
        StringBuilder sb = new StringBuilder();
        sb.append(TARGET_SUM).append(" = ");
        for (item i : stack) {
            sb.append("Column: " + i.getColumn() + "value: " + i.getValue()).append("+");
        }
        System.out.println(sb.deleteCharAt(sb.length() - 1).toString());
    }
}