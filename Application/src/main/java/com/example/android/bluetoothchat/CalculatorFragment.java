package com.example.android.bluetoothchat;

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

import java.util.Arrays;

/**
 * Base ever calculator
 *
 * Created by Maxim Yastremsky on 05.10.2017.
 */
public class CalculatorFragment extends Fragment {

    private static final String TAG = CalculatorFragment.class.getSimpleName();

    private static final String ADD = "+";
    private static final String SUB = "-";
    private static final String MUL = "×";
    private static final String DIV = "÷";

    private static final String SINGS_REGEX = "[" + SUB + ADD + MUL + DIV + "]";

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

            KeyEvent keyEvent = null;

            switch (v.getId()) {
                case R.id.point:
                    if (!currentValue.contains(",") && !currentValue.isEmpty()) {
                        keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_COMMA);
                    }
                    break;
                case R.id.number_0:
                    if (!TextUtils.isEmpty(currentValue)) {
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
                BaseInputConnection inputConnection = new BaseInputConnection(mResultEditText, true);
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
                    calculateAndSetResult();

                    String[] result = currentValue.split(SINGS_REGEX);
                    Log.d(TAG, "onClick: " + Arrays.toString(result));
                    break;

                case R.id.operation_add: addOperationSignToResultView(ADD); break;
                case R.id.operation_sub: addOperationSignToResultView(SUB); break;
                case R.id.operation_mul: addOperationSignToResultView(MUL); break;
                case R.id.operation_div: addOperationSignToResultView(DIV); break;
            }
        }

        private String calculateAndSetResult() {

            return null;
        }
    };

    private boolean hasOperationSignInside(String s) {
        return s.contains(ADD) || s.contains(SUB) || s.contains(MUL) || s.contains(DIV);
    }

    private void addOperationSignToResultView(String newOpSign) {
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
            currentValue.replace(selectionStart - 1, selectionEnd, newOpSign);

        } else if (rightBorderChar != 0 && isCharAnOperationSign(rightBorderChar)) {
            currentValue.replace(selectionStart, selectionEnd + 1, newOpSign);

            // if we have selection without sign borders, we just replace
        } else if (selectionStart != selectionEnd) {
            currentValue.replace(selectionStart, selectionEnd, newOpSign);

            //normal case: we just add a sign
        } else {
            currentValue.insert(selectionStart, newOpSign);
        }
    }

    private boolean isCharAnOperationSign(char c) {
        String charString = new String(new char[]{c});
        return hasOperationSignInside(charString);
    }

}
