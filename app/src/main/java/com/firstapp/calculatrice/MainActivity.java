package com.firstapp.calculatrice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    ArrayList<Object> operation = new ArrayList<>();
    ArrayList<Object> display= new ArrayList<>();

    Button zero,one,two,three,four,five,six,seven,eight,nine;
    TextView operationHistory,result;
    Button equal,divide,add,sub,modulo,multiply,divX,pow,square,clear,clearElement,delete;
    Button addSub,comma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    //definition des elements:
        zero=findViewById(R.id.zero);
        one=findViewById(R.id.one);
        two=findViewById(R.id.two);
        three=findViewById(R.id.three);
        four=findViewById(R.id.four);
        five=findViewById(R.id.five);
        six=findViewById(R.id.six);
        seven=findViewById(R.id.seven);
        eight=findViewById(R.id.eight);
        nine=findViewById(R.id.nine);

        operationHistory=findViewById(R.id.operation);
        result=findViewById(R.id.resultat);

        equal=findViewById(R.id.equal);
        divide=findViewById(R.id.division);
        add=findViewById(R.id.add);
        sub=findViewById(R.id.subtract);
        multiply=findViewById(R.id.multiply);
        modulo=findViewById(R.id.modulo);
        divX=findViewById(R.id.divideX);
        pow=findViewById(R.id.pow2);
        square=findViewById(R.id.racine);
        clear=findViewById(R.id.C);
        clearElement=findViewById(R.id.CE);
        delete=findViewById(R.id.delete);

        addSub=findViewById(R.id.addSub);
        comma=findViewById(R.id.comma);

        delete.setOnClickListener(view -> {
            if (!display.isEmpty()) {
                display.remove(display.size() - 1);
                Display(display);
            }
        });

        addSub.setOnClickListener(view -> {
            StringBuilder sb = new StringBuilder();
            for (Object element : display)
                sb.append(element);
            double value = Double.parseDouble(sb.toString());
            value*=-1;
            display.clear();
            display.add(value);
            Display(display);
        });

        zero.setOnClickListener(view -> {
            display.add(0);
            Display(display);
        });

        one.setOnClickListener(view -> {
            display.add(1);
            Display(display);
        });

        two.setOnClickListener(view -> {
            display.add(2);
            Display(display);
        });

        three.setOnClickListener(view ->{
                display.add(3);
                Display(display);
        });

        four.setOnClickListener(view -> {
            display.add(4);
            Display(display);
        });
        five.setOnClickListener(view -> {
            display.add(5);
            Display(display);
        });
        six.setOnClickListener(view -> {
            display.add(6);
            Display(display);
        });
        seven.setOnClickListener(view -> {
            display.add(7);
            Display(display);
        });
        eight.setOnClickListener(view -> {
            display.add(8);
            Display(display);
        });
        nine.setOnClickListener(view -> {
            display.add(9);
            Display(display);
        });

        comma.setOnClickListener(view -> {
            display.add('.');
            Display(display);
        });

        add.setOnClickListener(view -> {
            operation.addAll(display);
            operation.add('+');
            DisplayOperation(operation);
            display.clear();
            Display(display);
        });

        sub.setOnClickListener(view -> {
            operation.addAll(display);
            operation.add('-');
            DisplayOperation(operation);
            display.clear();
            Display(display);
        });
        multiply.setOnClickListener(view -> {
            operation.addAll(display);
            operation.add('*');
            DisplayOperation(operation);
            display.clear();
            Display(display);
        });
        divide.setOnClickListener(view -> {
            operation.addAll(display);
            operation.add('/');
            DisplayOperation(operation);
            display.clear();
            Display(display);
        });
        modulo.setOnClickListener(view -> {
            operation.addAll(display);
            operation.add('%');
            DisplayOperation(operation);
            display.clear();
            Display(display);
        });
        pow.setOnClickListener(view -> {
            StringBuilder sb = new StringBuilder();
            for (Object element : display)
                sb.append(element);
            double value = Double.parseDouble(sb.toString());
            value*=value;
            display.clear();
            display.add(value);
            Display(display);

        });

        divX.setOnClickListener(view -> {
            StringBuilder sb = new StringBuilder();
            for (Object element : display)
                sb.append(element);
            double value = Double.parseDouble(sb.toString());
            value=1/value;
            display.clear();
            display.add(value);
            Display(display);
        });

        square.setOnClickListener(view -> {
            StringBuilder sb = new StringBuilder();
            for (Object element : display)
                sb.append(element);
            double value = Double.parseDouble(sb.toString());
            value=Math.sqrt(value);
            display.clear();
            display.add(value);
            Display(display);
        });
        clearElement.setOnClickListener(view -> {
            display.clear();
            Display(display);
        });

        clear.setOnClickListener(view -> {
            operation.clear();
            display.clear();
            DisplayOperation(operation);
            Display(display);
        });
        delete.setOnClickListener(view -> {
            if(display.size()>0)
                display.remove(display.size() - 1);
            Display(display);
        });

        equal.setOnClickListener(view -> {
            operation.addAll(display);
            DisplayOperation(operation);
            display.clear();
            operation=OperationBuilder(operation);
            double value=evaluateExpression(operation);
            display.add(value);
            Display(display);

        });

    }

    void Display(ArrayList<Object> list){
        StringBuilder sb = new StringBuilder();
        for (Object element : list)
            sb.append(element);
        result.setText(sb.toString());
    }
    void DisplayOperation(ArrayList<Object> list){
        StringBuilder sb = new StringBuilder();
        for (Object element : list)
            sb.append(element);
        operationHistory.setText(sb.toString());
    }
    public ArrayList<Object> OperationBuilder(ArrayList<Object> list) {
        if (list == null || list.isEmpty()) return new ArrayList<>();

        ArrayList<Object> result = new ArrayList<>();
        StringBuilder numberBuilder = new StringBuilder();

        for (Object item : list) {
            String itemString = item.toString();
            if (isNumericString(itemString) || itemString.equals(".")) {
                // Si l'élément est une chaîne numérique ou un séparateur décimal, ajoutez-le au nombre en cours de construction
                numberBuilder.append(itemString);
            } else {
                // Si l'élément n'est pas numérique, ajoutez le nombre construit, puis l'opérateur
                if (numberBuilder.length() > 0) {
                    result.add(Double.parseDouble(numberBuilder.toString()));
                    numberBuilder.setLength(0);
                }
                result.add(item);
            }
        }

        // Ajoutez le dernier nombre construit (s'il y en a un)
        if (numberBuilder.length() > 0) {
            result.add(Double.parseDouble(numberBuilder.toString()));
        }

        return result;
    }

    private boolean isNumericString(String itemString) {
        try {
            Double.parseDouble(itemString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static double evaluateExpression(ArrayList<Object> expression) {
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (Object element : expression) {
            if (element instanceof Double) {
                numbers.push((Double) element);
            } else if (element instanceof Character) {
                char operator = (Character) element;

                while (!operators.isEmpty() && hasPrecedence(operators.peek(), operator)) {
                    double b = numbers.pop();
                    double a = numbers.pop();
                    char op = operators.pop();

                    double result = applyOperator(a, b, op);
                    numbers.push(result);
                }

                operators.push(operator);
            }
        }

        while (!operators.isEmpty()) {
            double b = numbers.pop();
            double a = numbers.pop();
            char op = operators.pop();

            double result = applyOperator(a, b, op);
            numbers.push(result);
        }

        if (numbers.size() == 1 && operators.isEmpty()) {
            return numbers.pop();
        } else {
            throw new IllegalArgumentException("Invalid expression");
        }
    }

    private static boolean hasPrecedence(char op1, char op2) {
        return (op1 != '+' && op1 != '-') || (op2 != '*' && op2 != '/' && op2 != '%');
    }

    private static double applyOperator(double a, double b, char operator) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return a / b;

            case '%':
                if (b == 0) {
                    throw new ArithmeticException("Modulo by zero");
                }
                return a % b;
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
    }

}