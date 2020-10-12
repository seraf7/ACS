#include <stdio.h>
//Libreria estandar de Linux
#include <unistd.h>

int main(){
	printf("Humberto Serafin Castillo López\n");
	//Devuelve ID del proceso
	printf("The process ID is %d\n", getpid());
	//Devuelve ID del proceso padre
	printf("The parent process ID is %d\n", getppid());
	return 0;
}