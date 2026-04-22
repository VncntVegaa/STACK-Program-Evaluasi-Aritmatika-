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
    if op in ('+', '-'):
        return 1
    if op in ('*', '/'):
        return 2
    if op == '^':
        return 3
    return 0


def is_operator(ch):
    return ch in '+-*/^'


def infix_to_postfix(infix):
    postfix = []
    stack = Stack()
    
    print("\n" + "="*50)
    print("STEP-BY-STEP KONVERSI INFIX KE POSTFIX")
    print("="*50)
    print(f"{'Input':<10} {'Stack':<20} {'Postfix':<20} Keterangan")
    print("-"*70)
    
    i = 0
    while i < len(infix):
        ch = infix[i]
        
        # 🔢 Ambil angka lebih dari 1 digit
        if ch.isdigit():
            num = ch
            while i + 1 < len(infix) and infix[i + 1].isdigit():
                i += 1
                num += infix[i]
            
            postfix.append(num)
            print(f"{num:<10} {str(stack):<20} {' '.join(postfix):<20} Operand → langsung ke postfix")
        
        elif ch == '(':
            stack.push(ch)
            print(f"{ch:<10} {str(stack):<20} {' '.join(postfix):<20} Kurung buka → push ke stack")
        
        elif ch == ')':
            print(f"{ch:<10} {str(stack):<20} {' '.join(postfix):<20} Kurung tutup → pop sampai '('")
            
            while not stack.is_empty() and stack.peek() != '(':
                postfix.append(stack.pop())
                print(f"{'':<10} {str(stack):<20} {' '.join(postfix):<20} Pop operator ke postfix")
            
            stack.pop()  # buang '('
            print(f"{'':<10} {str(stack):<20} {' '.join(postfix):<20} Pop '('")
        
        elif is_operator(ch):
            print(f"{ch:<10} {str(stack):<20} {' '.join(postfix):<20} Operator diproses")
            
            while (not stack.is_empty() and 
                   stack.peek() != '(' and 
                   precedence(stack.peek()) >= precedence(ch)):
                postfix.append(stack.pop())
                print(f"{'':<10} {str(stack):<20} {' '.join(postfix):<20} Pop prioritas lebih tinggi/sama")
            
            stack.push(ch)
            print(f"{'':<10} {str(stack):<20} {' '.join(postfix):<20} Push operator")
        
        i += 1
    
    # 🔄 Pop sisa operator
    while not stack.is_empty():
        postfix.append(stack.pop())
        print(f"{'':<10} {str(stack):<20} {' '.join(postfix):<20} Pop sisa operator")
    
    print("="*50 + "\n")
    return postfix


def evaluate_postfix(postfix):
    stack = Stack()
    
    print("\n" + "="*50)
    print("STEP-BY-STEP EVALUASI POSTFIX")
    print("="*50)
    print(f"{'Input':<10} {'Stack':<30} Operasi")
    print("-"*60)
    
    for ch in postfix:
        # 🔢 Jika angka (bisa lebih dari 1 digit)
        if ch.isdigit():
            stack.push(int(ch))
            print(f"{ch:<10} {str(stack):<30} Push {ch}")
        
        else:
            b = stack.pop()
            a = stack.pop()
            
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
                result = a // b
                operasi = f"{a} / {b} = {result}"
            elif ch == '^':
                result = a ** b
                operasi = f"{a} ^ {b} = {result}"
            
            stack.push(result)
            print(f"{ch:<10} {str(stack):<30} {operasi}")
    
    print("="*50 + "\n")
    return stack.pop()


def main():
    print("="*50)
    print("PROGRAM EVALUASI EKSPRESI ARITMATIKA")
    print("="*50)
    
    infix = input("Masukkan ekspresi infix: ")
    infix = infix.replace(" ", "")
    
    print(f"\nEkspresi Infix: {infix}")
    
    postfix = infix_to_postfix(infix)
    print(f"POSTFIX EXPRESSION: {' '.join(postfix)}")
    
    hasil = evaluate_postfix(postfix)
    
    print("="*50)
    print(f"HASIL AKHIR: {hasil}")
    print("="*50)


if __name__ == "__main__":
    main()
