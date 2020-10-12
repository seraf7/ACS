#include <stdio.h>
#include <unistd.h>

int main(){
	//Variable para ID de proceso
	pid_t id_hijo;

	printf("1: el ID del proceso main es %d\n", getpid());

	//Creacion de nuevo proceso
	id_hijo = fork();
	printf("2: este printf saldrá duplicado\n");
	printf("3: el ID del proceso hijo es %d\n", id_hijo);

	//Distinción entre procesos
	if(id_hijo != 0){
		printf("\nPADRE: proceso main con ID %d\n", getpid());
		printf("PADRE: ID del proceso hijo es %d\n", id_hijo);
		printf("PADRE: ID del padre del padre %d\n", getppid());
	}
	else{
		printf("\nHIJO: proceso hijo con ID %d\n", getpid());
		printf("HIJO: ID del hijo es %d\n", id_hijo);
		printf("HIJO: ID del padre es %d\n", getppid());
	}

	return 0;
}