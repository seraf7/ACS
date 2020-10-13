/*myecho.c*/

#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]){
	int j;

	//Para imprimir su lista de argumentos
	for(j = 0; j<argc; j++){
		printf("argv[%d]: %s\n", j, argv[j]);
	}

	exit(EXIT_SUCCESS);
}