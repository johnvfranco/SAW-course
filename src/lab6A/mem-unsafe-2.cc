/** 
    memory-unsafe-6.cc

    Memory safety:
    1. No variable in a stack references an uninitialised object
       (can't dereference a null object)
    2. No variable in a stack references an object whose lifetime is shorter
       (an illegal reference may result)
*** 3. All references respect permissions

**/
#include <iostream>
using namespace std;

class A {
private:
   int a,b,c;

public:
   A (int x) { a = x; b=x+20; c=x+31; }
   int get() { return a; }
};

int main () {
   A *a = new A(125);
   cout << a->get() << "\n";
   /** cout << a->a << "\n"; **/ /* compiler error - private access!! */
   *((int*)a+2) = 876;               /* Huh? */
   cout << *(int*)a << "\n";         /* Huh? */
   cout << *((int*)a+1) << "\n";     /* Huh? */
   cout << *((int*)a+2) << "\n";     /* Huh? */
}
