#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>

//Definicion de la funcion spawn
int spawn(char* program, char** arg_list){
	//Variable de tipo pid_t, para ID de proceso
	pid_t child_pid;
	//Creación de un nuevo proceso
	child_pid = fork();

	//Validar que es el proceso padre
	if(child_pid != 0){
		//retorno de la funcion con el ID del hijo
		return child_pid;
	}
	//Caso para el proceso hijo 
	else{
		//Se crea un nuevo proceso, matanto al proceso actual
		//nuevo proceso llama a la funcion ls con la lista
		//de argumentos proporcionada
		execvp(program, arg_list);
		//Se imprime el error si fallo el uso de execvp
		fprintf(stderr, "an error ocurred in execvp\n");
		//Finaliza el programa de manera anormal
		//no retorna ningun valor
		abort();
	}
}

int main(){
	//Definicion la lista de argumentos usada por spawn
	char* arg_list[] = {
		"ls",
		"-l",
		"/",
		NULL	//Indica final de la lista
	};

	//Llamado a spawn con el comando ls y la lista de argumentos definida
	spawn("ls", arg_list);
	//Impresion de final del programa principal
	printf("done with main program\n");
	//Valor de retorno para finalizacion exitosa
	return 0;
}