import java.util.*;

class StackCustom {
    private List<String> items = new ArrayList<>();

    public void push(String item) {
        items.add(item);
    }

    public String pop() {
        if (!isEmpty()) {
            return items.remove(items.size() - 1);
        }
        return null;
    }

    public String peek() {
        if (!isEmpty()) {
            return items.get(items.size() - 1);
        }
        return null;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public String toString() {
        return items.toString();
    }
}

public class EvaluasiEkspresi {

    // Prioritas operator
    public static int precedence(String op) {
        if (op.equals("+") || op.equals("-")) return 1;
        if (op.equals("*") || op.equals("/")) return 2;
        if (op.equals("^")) return 3;
        return 0;
    }

    public static boolean isOperator(String ch) {
        return "+-*/^".contains(ch);
    }

    // 🔄 INFIX → POSTFIX
    public static List<String> infixToPostfix(String infix) {
        List<String> postfix = new ArrayList<>();
        StackCustom stack = new StackCustom();

        System.out.println("\n" + "=".repeat(50));
        System.out.println("STEP-BY-STEP KONVERSI INFIX KE POSTFIX");
        System.out.println("=".repeat(50));
        System.out.printf("%-10s %-20s %-20s %s\n", "Input", "Stack", "Postfix", "Keterangan");
        System.out.println("-".repeat(70));

        int i = 0;
        while (i < infix.length()) {
            char ch = infix.charAt(i);

            // 🔢 Ambil angka lebih dari 1 digit
            if (Character.isDigit(ch)) {
                String num = "" + ch;
                while (i + 1 < infix.length() && Character.isDigit(infix.charAt(i + 1))) {
                    i++;
                    num += infix.charAt(i);
                }

                postfix.add(num);
                System.out.printf("%-10s %-20s %-20s %s\n",
                        num, stack.toString(), String.join(" ", postfix),
                        "Operand → langsung ke postfix");
            }

            else if (ch == '(') {
                stack.push("(");
                System.out.printf("%-10s %-20s %-20s %s\n",
                        ch, stack.toString(), String.join(" ", postfix),
                        "Kurung buka → push ke stack");
            }

            else if (ch == ')') {
                System.out.printf("%-10s %-20s %-20s %s\n",
                        ch, stack.toString(), String.join(" ", postfix),
                        "Kurung tutup → pop sampai '('");

                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    postfix.add(stack.pop());
                    System.out.printf("%-10s %-20s %-20s %s\n",
                            "", stack.toString(), String.join(" ", postfix),
                            "Pop operator ke postfix");
                }

                stack.pop(); // buang '('
                System.out.printf("%-10s %-20s %-20s %s\n",
                        "", stack.toString(), String.join(" ", postfix),
                        "Pop '('");
            }

            else if (isOperator(String.valueOf(ch))) {
                String op = String.valueOf(ch);

                System.out.printf("%-10s %-20s %-20s %s\n",
                        op, stack.toString(), String.join(" ", postfix),
                        "Operator diproses");

                while (!stack.isEmpty() &&
                        !stack.peek().equals("(") &&
                        precedence(stack.peek()) >= precedence(op)) {

                    postfix.add(stack.pop());
                    System.out.printf("%-10s %-20s %-20s %s\n",
                            "", stack.toString(), String.join(" ", postfix),
                            "Pop prioritas lebih tinggi/sama");
                }

                stack.push(op);
                System.out.printf("%-10s %-20s %-20s %s\n",
                        "", stack.toString(), String.join(" ", postfix),
                        "Push operator");
            }

            i++;
        }

        // Pop sisa operator
        while (!stack.isEmpty()) {
            postfix.add(stack.pop());
            System.out.printf("%-10s %-20s %-20s %s\n",
                    "", stack.toString(), String.join(" ", postfix),
                    "Pop sisa operator");
        }

        System.out.println("=".repeat(50) + "\n");
        return postfix;
    }

    // 🧮 EVALUASI POSTFIX
    public static int evaluatePostfix(List<String> postfix) {
        Stack<Integer> stack = new Stack<>();

        System.out.println("\n" + "=".repeat(50));
        System.out.println("STEP-BY-STEP EVALUASI POSTFIX");
        System.out.println("=".repeat(50));
        System.out.printf("%-10s %-30s %s\n", "Input", "Stack", "Operasi");
        System.out.println("-".repeat(60));

        for (String token : postfix) {

            if (token.matches("\\d+")) {
                stack.push(Integer.parseInt(token));
                System.out.printf("%-10s %-30s %s\n",
                        token, stack.toString(), "Push " + token);
            } else {
                int b = stack.pop();
                int a = stack.pop();
                int result = 0;
                String operasi = "";

                switch (token) {
                    case "+":
                        result = a + b;
                        operasi = a + " + " + b + " = " + result;
                        break;
                    case "-":
                        result = a - b;
                        operasi = a + " - " + b + " = " + result;
                        break;
                    case "*":
                        result = a * b;
                        operasi = a + " * " + b + " = " + result;
                        break;
                    case "/":
                        result = a / b;
                        operasi = a + " / " + b + " = " + result;
                        break;
                    case "^":
                        result = (int) Math.pow(a, b);
                        operasi = a + " ^ " + b + " = " + result;
                        break;
                }

                stack.push(result);
                System.out.printf("%-10s %-30s %s\n",
                        token, stack.toString(), operasi);
            }
        }

        System.out.println("=".repeat(50) + "\n");
        return stack.pop();
    }

    // 🚀 MAIN
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=".repeat(50));
        System.out.println("PROGRAM EVALUASI EKSPRESI ARITMATIKA");
        System.out.println("=".repeat(50));

        System.out.print("Masukkan ekspresi infix: ");
        String infix = sc.nextLine().replace(" ", "");

        System.out.println("\nEkspresi Infix: " + infix);

        List<String> postfix = infixToPostfix(infix);
        System.out.println("POSTFIX EXPRESSION: " + String.join(" ", postfix));

        int hasil = evaluatePostfix(postfix);

        System.out.println("=".repeat(50));
        System.out.println("HASIL AKHIR: " + hasil);
        System.out.println("=".repeat(50));
    }
}
