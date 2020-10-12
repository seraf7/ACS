#include <stdio.h>
#include <stdlib.h>

int main(void){
	//Variable para ID de proceso
	pid_t child;

	//Crea un nuevo proceso, guarda el valor devuelto por fork
	//Valida si ocurrió un error al ejecutar fork
	if((child = fork()) == -1){
		//Impresión descriptiva del de error ocurrido
		//primero la palabra fork, seguida la descripción
		perror("fork");
		//Finaliza la ejecución el programa,
		//indicando una finalización sin éxito
		exit(EXIT_FAILURE);
	}
	//Validar que se esta en el proceso hijo
	else if(child == 0){
		//Impresión con salto de línea
		puts("in child");
		//Impresión del ID del proceso
		printf("\tchild pid = %d\n", getpid());
		//Impresión del ID del padre
		printf("\tchild ppid = %d\n", getppid());
		//Finaliza la ejecuión del programa,
		//indicando una finalización exitosa
		exit(EXIT_SUCCESS);
	}
	//Caso para el proceso padre
	else{
		//Impresión con salto de línea
		puts("in parent");
		//Impresión del ID del proceso
		printf("\tparent pid = %d\n", getpid());
		//Impresión del ID del padre
		printf("\tparent ppid = %d\n", getppid());
	}
	//Finalización exitosa del programa
	exit(EXIT_SUCCESS);
}