#include <stdio.h>
#include <unistd.h>

int main(){
 	int retorno_fork;
 	int arg_wait;
 	int pid = getpid();
 	printf( "Iniciando proceso con pid %d\n", pid );
 	printf( "Llamando a fork la primera vez\n" );
	retorno_fork = fork();
 
 	if( retorno_fork != 0 ){
		printf( "Proceso padre, la variable retorno_fork la primera vez=%d\n",retorno_fork );
 		printf( "Llamando a fork la segunda vez\n" );
 		retorno_fork = fork();
 	
	 	if( retorno_fork != 0 ){
		 	printf( "Proceso padre, llamando a wait\n" );
		 	//Guarda ID del proceso que termina
		 	int retorno_wait = wait( &arg_wait );
		 	printf( "Proceso padre, despues de wait\n" );
		 	printf( "Proceso padre, el valor &arg_wait=%X\n",&arg_wait);
		 	printf( "Proceso padre, la variable arg_wait=%d\n",arg_wait);
		 	printf( "Proceso padre, la variable retorno_wait=%d\n",retorno_wait );
	 	}
	 	else{
		 	printf( "Proceso hijo 2, el pid es %d\n", getpid() );
		 	printf( "Proceso hijo 2, a dormir 5 seg\n" );
		 	sleep( 5 );
		 	printf( "Proceso hijo 2, despierta\n" );
	 	}
 	}
 	else{
	 	printf( "Proceso hijo 1, el pid es %d\n", getpid() );
	 	printf( "Proceso hijo 1, a dormir 10 seg\n" );
	 	sleep( 10 );
	 	printf( "Proceso hijo 1, despierta\n");
	 	printf( "Padre de Proceso hijo 1, el ppid es %d\n", getppid() );
 	}
}