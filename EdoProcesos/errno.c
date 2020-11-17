#include <stdio.h>

main(){
	int i;
	//Impresion de los codigos de error del sistema
	for(i = 0; i < sys_nerr; i++){
		printf("%d: %s\n", i, sys_errlist[i]);
	}
}
