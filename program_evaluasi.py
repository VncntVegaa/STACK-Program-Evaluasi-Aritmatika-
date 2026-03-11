class Stack:
    def __init__(self):
        self.items = []
    
    def push(self, item):
        self.items.append(item)
    
    def pop(self):
        if not self.is_empty():
            return self.items.pop()
        return None
    
    def peek(self):
        if not self.is_empty():
            return self.items[-1]
        return None
    
    def is_empty(self):
        return len(self.items) == 0
    
    def __str__(self):
        return str(self.items)

def precedence(op):
    """Menentukan prioritas operator"""
    if op in ('+', '-'):
        return 1
    if op in ('*', '/'):
        return 2
    if op == '^':
        return 3
    return 0

def is_operator(ch):
    """Mengecek apakah karakter adalah operator"""
    return ch in '+-*/^'

def infix_to_postfix(infix):
    """Konversi infix ke postfix dengan step-by-step"""
    postfix = []
    stack = Stack()
    
    print("\n" + "="*50)
    print("STEP-BY-STEP KONVERSI INFIX KE POSTFIX")
    print("="*50)
    print(f"{'Input':<10} {'Stack':<20} {'Postfix':<20} Keterangan")
    print("-"*70)
    
    for ch in infix:
        keterangan = ""
        
        if ch.isdigit():
            postfix.append(ch)
            keterangan = "Operand → langsung ke postfix"
            print(f"{ch:<10} {str(stack):<20} {''.join(postfix):<20} {keterangan}")
        
        elif ch == '(':
            stack.push(ch)
            keterangan = "Kurung buka → push ke stack"
            print(f"{ch:<10} {str(stack):<20} {''.join(postfix):<20} {keterangan}")
        
        elif ch == ')':
            keterangan = "Kurung tutup → pop sampai '('"
            print(f"{ch:<10} {str(stack):<20} {''.join(postfix):<20} {keterangan}")
            
            while not stack.is_empty() and stack.peek() != '(':
                postfix.append(stack.pop())
                print(f"{'':<10} {str(stack):<20} {''.join(postfix):<20} {'Pop operator ke postfix'}")
            
            if not stack.is_empty() and stack.peek() == '(':
                stack.pop()  # buang '('
                print("{'':<10} {str(stack):<20} {''.join(postfix):<20} {'Pop '('}")
        
        elif is_operator(ch):
            keterangan = "Operator → push setelah pop prioritas lebih tinggi/sama"
            print(f"{ch:<10} {str(stack):<20} {''.join(postfix):<20} {keterangan}")
            
            while (not stack.is_empty() and 
                   stack.peek() != '(' and 
                   precedence(stack.peek()) >= precedence(ch)):
                postfix.append(stack.pop())
                print(f"{'':<10} {str(stack):<20} {''.join(postfix):<20} {'Pop operator prioritas lebih tinggi/sama'}")
            
            stack.push(ch)
            print(f"{'':<10} {str(stack):<20} {''.join(postfix):<20} {'Push operator baru'}")
    
    # Pop semua sisa operator di stack
    while not stack.is_empty():
        postfix.append(stack.pop())
        print(f"{'':<10} {str(stack):<20} {''.join(postfix):<20} {'Pop sisa operator di stack'}")
    
    print("="*50 + "\n")
    return ''.join(postfix)

def evaluate_postfix(postfix):
    """Evaluasi postfix dengan step-by-step"""
    stack = Stack()
    
    print("\n" + "="*50)
    print("STEP-BY-STEP EVALUASI POSTFIX")
    print("="*50)
    print(f"{'Input':<10} {'Stack':<30} Operasi")
    print("-"*60)
    
    for ch in postfix:
        if ch.isdigit():
            stack.push(int(ch))
            print(f"{ch:<10} {str(stack):<30} Push operand {ch}")
        else:
            b = stack.pop()
            a = stack.pop()
            result = 0
            operasi = ""
            
            if ch == '+':
                result = a + b
                operasi = f"{a} + {b} = {result}"
            elif ch == '-':
                result = a - b
                operasi = f"{a} - {b} = {result}"
            elif ch == '*':
                result = a * b
                operasi = f"{a} * {b} = {result}"
            elif ch == '/':
                result = a // b  # integer division
                operasi = f"{a} / {b} = {result}"
            elif ch == '^':
                result = a ** b
                operasi = f"{a} ^ {b} = {result}"
            
            stack.push(result)
            print(f"{ch:<10} {str(stack):<30} {operasi}")
    
    print("="*50 + "\n")
    return stack.pop()

def main():
    """Program utama"""
    print("="*50)
    print("PROGRAM EVALUASI EKSPRESI ARITMATIKA")
    print("="*50)
    
    infix = input("Masukkan ekspresi infix: ")
    # Hapus spasi jika ada
    infix = infix.replace(" ", "")
    
    print(f"\nEkspresi Infix: {infix}")
    
    # Konversi ke postfix
    postfix = infix_to_postfix(infix)
    print(f"POSTFIX EXPRESSION: {postfix}")
    
    # Evaluasi postfix
    hasil = evaluate_postfix(postfix)
    print("="*50)
    print(f"HASIL AKHIR: {hasil}")
    print("="*50)

if __name__ == "__main__":
    main()