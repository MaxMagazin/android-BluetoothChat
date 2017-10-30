package com.example.android.bluetoothchat;

import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.BaseInputConnection;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * Base ever calculator
 *
 * Created by Maxim Yastremsky on 05.10.2017.
 */
public class CalculatorFragment extends Fragment {

    private static final String TAG = CalculatorFragment.class.getSimpleName();

    private static final char ADD = '+';
    private static final char SUB = '-';
    private static final char MUL = '×';
    private static final char DIV = '÷';

    private EditText mResultEditText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.calculator_fragment, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mResultEditText = (EditText) view.findViewById(R.id.calc_out);

        view.findViewById(R.id.number_0).setOnClickListener(mNumbersAndPointClickListener);
        view.findViewById(R.id.number_1).setOnClickListener(mNumbersAndPointClickListener);
        view.findViewById(R.id.number_2).setOnClickListener(mNumbersAndPointClickListener);
        view.findViewById(R.id.number_3).setOnClickListener(mNumbersAndPointClickListener);
        view.findViewById(R.id.number_4).setOnClickListener(mNumbersAndPointClickListener);
        view.findViewById(R.id.number_5).setOnClickListener(mNumbersAndPointClickListener);
        view.findViewById(R.id.number_6).setOnClickListener(mNumbersAndPointClickListener);
        view.findViewById(R.id.number_7).setOnClickListener(mNumbersAndPointClickListener);
        view.findViewById(R.id.number_8).setOnClickListener(mNumbersAndPointClickListener);
        view.findViewById(R.id.number_9).setOnClickListener(mNumbersAndPointClickListener);
        view.findViewById(R.id.point).setOnClickListener(mNumbersAndPointClickListener);
        view.findViewById(R.id.point).requestFocus();

        view.findViewById(R.id.operation_add).setOnClickListener(mOperationOrderClickListener);
        view.findViewById(R.id.operation_div).setOnClickListener(mOperationOrderClickListener);
        view.findViewById(R.id.operation_mul).setOnClickListener(mOperationOrderClickListener);
        view.findViewById(R.id.operation_sub).setOnClickListener(mOperationOrderClickListener);
        view.findViewById(R.id.operation_equal).setOnClickListener(mOperationOrderClickListener);

        view.findViewById(R.id.operation_clean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultEditText.setText("");
            }
        });

        view.findViewById(R.id.operation_clear_entry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable text = mResultEditText.getText();
                if (text.length() > 0) {
                    BaseInputConnection inputConnection = new BaseInputConnection(mResultEditText, true);
                    inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                }
            }
        });
        view.findViewById(R.id.operation_memory_add).setOnClickListener(mOperationOrderClickListener);
        view.findViewById(R.id.operation_change_sign).setOnClickListener(mOperationOrderClickListener);
    }

    private View.OnClickListener mNumbersAndPointClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String currentValue = mResultEditText.getText().toString();

            BaseInputConnection inputConnection = new BaseInputConnection(mResultEditText, true);

            KeyEvent keyEvent = null;

            switch (v.getId()) {
                case R.id.point:
                    int selStart = mResultEditText.getSelectionStart();

                    if (!TextUtils.isEmpty(currentValue)) {
                        int selEnd = mResultEditText.getSelectionEnd();

                        if (selStart != selEnd) { //remove selection
                            currentValue = new StringBuilder(currentValue).replace(selStart, selEnd, "").toString();
                        }

                        if (hasOperationSignInside(currentValue)) {

                            int numberStartIndex, numberEndIndex;
                            numberStartIndex = numberEndIndex = selStart;

                            char[] chars = currentValue.toCharArray();

                            // look for the startIndex of number
                            for (int i = selStart - 1; i >= 0; i--) {
                                if (!isCharAnOperationSign(chars[i])) {
                                    numberStartIndex = i;
                                } else {
                                    break;
                                }
                            }

                            //look for the endIndex of number
                            for (int i = selStart; i < currentValue.length(); i++) {
                                if (!isCharAnOperationSign(chars[i])) {
                                    numberEndIndex = i + 1;
                                } else {
                                    break;
                                }
                            }

                            // reduce only to the number
                            currentValue =  currentValue.substring(numberStartIndex, numberEndIndex);

                            //update selStart
                            selStart -= numberStartIndex;
                        }
                    }

                    if (!currentValue.contains(",")) {
                        if (selStart == 0) { // add leading 0
                            inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_0));
                            // this action will also remove a selection, if it was there
                        }

                        keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_COMMA);
                    }

                    break;
                case R.id.number_0:
                    if (TextUtils.isEmpty(currentValue)) {
                        keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_0);
                    }
                    break;
                case R.id.number_1: keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_1); break;
                case R.id.number_2: keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_2); break;
                case R.id.number_3: keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_3); break;
                case R.id.number_4: keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_4); break;
                case R.id.number_5: keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_5); break;
                case R.id.number_6: keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_6); break;
                case R.id.number_7: keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_7); break;
                case R.id.number_8: keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_8); break;
                case R.id.number_9: keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_9); break;
            }

            if (keyEvent != null) {
                inputConnection.sendKeyEvent(keyEvent);
            }
        }

    };

    private View.OnClickListener mOperationOrderClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String currentValue = mResultEditText.getText().toString();

            boolean hasOperationInside = hasOperationSignInside(currentValue);


            switch (v.getId()) {
                case R.id.operation_equal:
                    calculateAndSetResult(currentValue);


                    break;

                case R.id.operation_add: addOperationSignToResultView(ADD); break;
                case R.id.operation_sub: addOperationSignToResultView(SUB); break;
                case R.id.operation_mul: addOperationSignToResultView(MUL); break;
                case R.id.operation_div: addOperationSignToResultView(DIV); break;
            }
        }

        private void calculateAndSetResult(String currentValue) {
            currentValue = currentValue.replaceAll(",", ".");
            double result = eval(currentValue);

            DecimalFormat df = new DecimalFormat("###.###########");
            String calcResult = df.format(result);
            mResultEditText.setText("");
            mResultEditText.append(calcResult.replaceAll("\\.", ","));
        }
    };

    private boolean hasOperationSignInside(String s) {
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (isCharAnOperationSign(c)) {
                return true;
            }
        }
        return false;
    }

    private void addOperationSignToResultView(char newOpSign) {
        if (mResultEditText == null) {
            return;
        }

        Editable currentValue = mResultEditText.getText();

        int selectionStart = mResultEditText.getSelectionStart();
        int selectionEnd = mResultEditText.getSelectionEnd();

        // cursor is in the beginning (applies also for empty string)
        if (selectionStart == 0) {
            return;
        }

        // check if the cursor/selection borders on any operation sign, if yes we update it
        char leftBorderChar = currentValue.charAt(selectionStart - 1);

        char rightBorderChar = 0;
        if (selectionEnd < currentValue.length()) {
            rightBorderChar = currentValue.charAt(selectionEnd);
        }

        if (isCharAnOperationSign(leftBorderChar)) {
            currentValue.replace(selectionStart - 1, selectionEnd, String.valueOf(newOpSign));

        } else if (rightBorderChar != 0 && isCharAnOperationSign(rightBorderChar)) {
            currentValue.replace(selectionStart, selectionEnd + 1, String.valueOf(newOpSign));

            // if we have selection without sign borders, we just replace
        } else if (selectionStart != selectionEnd) {
            currentValue.replace(selectionStart, selectionEnd, String.valueOf(newOpSign));

            //normal case: we just add a sign
        } else {
            currentValue.insert(selectionStart, String.valueOf(newOpSign));
        }
    }

    private boolean isCharAnOperationSign(char c) {
        return c == ADD || c == SUB || c == MUL || c == DIV;
    }

    public double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)` | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat(ADD)) x += parseTerm(); // addition
                    else if (eat(SUB)) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat(MUL)) x *= parseFactor(); // multiplication
                    else if (eat(DIV)) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat(ADD)) return parseFactor(); // unary plus
                if (eat(SUB)) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }
}
