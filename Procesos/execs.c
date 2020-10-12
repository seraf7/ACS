#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>

int main(){
	//NULL indica el fin de argumentos en el arreglo
	char *args[] = {"/bin/ls", NULL};

	//NULL indica el fin de parametros para la funcion
	if(execve("/bin/ls", args, NULL) == -1){
		perror("execve");
		exit(EXIT_FAILURE);
	}

	puts("shouldn't get here");
	exit(EXIT_SUCCESS);
}