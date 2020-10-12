#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>

int main(){
	char *args[] = {"ls", "-l", "/home", "/opt", "/pruebas", NULL};

	if(execvp("ls", args) == -1){
		perror("execvp");
		exit(EXIT_FAILURE);
	}

	puts("shouldn't get here");
	exit(EXIT_SUCCESS);
}