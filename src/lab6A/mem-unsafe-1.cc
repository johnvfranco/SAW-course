/** 
     memory-unsafe-5.cc

     Memory safety:
     1. No variable in a stack references an uninitialised object
        (can't dereference a null object)
***  2. No variable in a stack references an object whose lifetime is shorter
***     (an illegal reference may result)
     3. All references respect permissions
**/
#include <iostream>
using namespace std;

class Object {
 private:
   int number;
   
 public:
   Object (int n) { number = n; }
   int getNumber () { return number; }
};

int main () {
   cout << "first object created, ";
   Object *object_1 = new Object(10);
   cout << "\tfirst's number: " << object_1->getNumber() << "\n";
   delete object_1;
   cout << "first object deleted, ";
   cout << "\tfirst's number: " << object_1->getNumber() << "\n";
   Object *object_2 = new Object(13);
   cout << "second object created, ";
   cout << "\tfirst's number: " << object_1->getNumber() << "\n";
   
}
