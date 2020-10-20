#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <signal.h>

int main(){
	pid_t pid;
	//Creacion de un prooceso hijo
	if (( pid = fork()) == 0) {
		//Ciclo infinito
		while(1) {
			printf("HIJO 1 getpid()=%d pid = %d\n", getpid(), pid);
			sleep(1);
		}
	 }
	 
	 //Creacion de un segundo proceso hijo
	 if (( pid = fork()) == 0) {
		//Ciclo infinito
		while(1) {
			printf("HIJO 2 getpid()=%d pid = %d\n", getpid(), pid);
			sleep(1);
		}
	 }
	 
	 sleep(10);
	 //Padre termina el proceso hijo 2
	 printf("PADRE Terminacion proceso %d\n", pid);
	 kill (pid,SIGTERM);
	 //Muerte natural del padre
	 exit(0);
}
