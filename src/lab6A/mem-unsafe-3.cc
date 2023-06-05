/** 
    memory-unsafe-3.c

    Memory safety:
    1. No variable in a stack references an uninitialised object
       (can't dereference a null object)
    2. No variable in a stack references an object whose lifetime is shorter
       (an illegal reference may result)
    3. All references respect permissions
**/
#include <iostream>
using namespace std;

class A {
public:
   A (int n) { number = n; }
   int number;
};

int main () {
   void *a = (void*)malloc(sizeof(A));
   cout << ((A*)a)->number << "\n";
}
