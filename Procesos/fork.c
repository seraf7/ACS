#include <stdio.h>
#include <unistd.h>

main (){
	//Variable de tipo ID proceso
	pid_t id_hijo;
	printf("1: el ID del proceso main es %d\n", getpid());

	//Creacion de nuevo proceso
	id_hijo = fork();
	printf("2: Este printf saldrá duplicado\n");
	printf("3: el ID dl proceso hijo es %d\n", id_hijo);
}