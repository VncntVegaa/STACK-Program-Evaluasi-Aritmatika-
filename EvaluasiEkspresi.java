import java.util.*;

public class EvaluasiEkspresi {
    
    // Menentukan prioritas operator
    static int precedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }
    
    // Mengecek apakah karakter adalah operator
    static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^';
    }
    
    // Konversi infix ke postfix dengan step-by-step
    static String infixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        
        System.out.println("\n==========================================");
        System.out.println("STEP-BY-STEP KONVERSI INFIX KE POSTFIX");
        System.out.println("==========================================");
        System.out.printf("%-10s %-20s %-20s %s\n", "Input", "Stack", "Postfix", "Keterangan");
        System.out.println("------------------------------------------------------------");
        
        for (int i = 0; i < infix.length(); i++) {
            char ch = infix.charAt(i);
            String keterangan = "";
            
            if (Character.isDigit(ch)) {
                postfix.append(ch);
                keterangan = "Operand → langsung ke postfix";
                System.out.printf("%-10c %-20s %-20s %s\n", ch, stack.toString(), postfix.toString(), keterangan);
            }
            else if (ch == '(') {
                stack.push(ch);
                keterangan = "Kurung buka → push ke stack";
                System.out.printf("%-10c %-20s %-20s %s\n", ch, stack.toString(), postfix.toString(), keterangan);
            }
            else if (ch == ')') {
                keterangan = "Kurung tutup → pop sampai '('";
                System.out.printf("%-10c %-20s %-20s %s\n", ch, stack.toString(), postfix.toString(), keterangan);
                
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop());
                    System.out.printf("%-10s %-20s %-20s %s\n", "", stack.toString(), postfix.toString(), "Pop operator ke postfix");
                }
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop(); // buang '('
                    System.out.printf("%-10s %-20s %-20s %s\n", "", stack.toString(), postfix.toString(), "Pop '('");
                }
            }
            else if (isOperator(ch)) {
                keterangan = "Operator → push setelah pop prioritas lebih tinggi/sama";
                System.out.printf("%-10c %-20s %-20s %s\n", ch, stack.toString(), postfix.toString(), keterangan);
                
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(ch)) {
                    postfix.append(stack.pop());
                    System.out.printf("%-10s %-20s %-20s %s\n", "", stack.toString(), postfix.toString(), "Pop operator prioritas lebih tinggi/sama");
                }
                stack.push(ch);
                System.out.printf("%-10s %-20s %-20s %s\n", "", stack.toString(), postfix.toString(), "Push operator baru");
            }
        }
        
        // Pop semua sisa operator di stack
        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
            System.out.printf("%-10s %-20s %-20s %s\n", "", stack.toString(), postfix.toString(), "Pop sisa operator di stack");
        }
        
        System.out.println("==========================================\n");
        return postfix.toString();
    }
    
    // Evaluasi postfix dengan step-by-step
    static int evaluatePostfix(String postfix) {
        Stack<Integer> stack = new Stack<>();
        
        System.out.println("\n==========================================");
        System.out.println("STEP-BY-STEP EVALUASI POSTFIX");
        System.out.println("==========================================");
        System.out.printf("%-10s %-30s %s\n", "Input", "Stack", "Operasi");
        System.out.println("------------------------------------------------------------");
        
        for (int i = 0; i < postfix.length(); i++) {
            char ch = postfix.charAt(i);
            
            if (Character.isDigit(ch)) {
                stack.push(ch - '0');
                System.out.printf("%-10c %-30s %s\n", ch, stack.toString(), "Push operand " + ch);
            } else {
                int b = stack.pop();
                int a = stack.pop();
                int result = 0;
                String operasi = "";
                
                switch (ch) {
                    case '+': 
                        result = a + b; 
                        operasi = a + " + " + b + " = " + result;
                        break;
                    case '-': 
                        result = a - b; 
                        operasi = a + " - " + b + " = " + result;
                        break;
                    case '*': 
                        result = a * b; 
                        operasi = a + " * " + b + " = " + result;
                        break;
                    case '/': 
                        result = a / b; 
                        operasi = a + " / " + b + " = " + result;
                        break;
                    case '^': 
                        result = (int) Math.pow(a, b); 
                        operasi = a + " ^ " + b + " = " + result;
                        break;
                }
                
                stack.push(result);
                System.out.printf("%-10c %-30s %s\n", ch, stack.toString(), operasi);
            }
        }
        
        System.out.println("==========================================\n");
        return stack.pop();
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("==========================================");
        System.out.println("PROGRAM EVALUASI EKSPRESI ARITMATIKA");
        System.out.println("==========================================");
        System.out.print("Masukkan ekspresi infix: ");
        String infix = sc.nextLine();
        
        // Hapus spasi jika ada
        infix = infix.replaceAll("\\s+", "");
        
        System.out.println("\nEkspresi Infix: " + infix);
        
        // Konversi ke postfix
        String postfix = infixToPostfix(infix);
        System.out.println("POSTFIX EXPRESSION: " + postfix);
        
        // Evaluasi postfix
        int hasil = evaluatePostfix(postfix);
        System.out.println("==========================================");
        System.out.println("HASIL AKHIR: " + hasil);
        System.out.println("==========================================");
        
        sc.close();
    }
}