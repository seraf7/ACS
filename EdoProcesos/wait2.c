#include <stdio.h>
#include <unistd.h>

int main(){
	int retorno_fork;
	int arg_wait;
	int pid = getpid();
	printf( "Iniciando proceso con pid %d\n", pid );
	printf( "Llamando a fork\n" );
	retorno_fork = fork();
	
	if( retorno_fork != 0 ){
		printf( "Proceso padre, la variable retorno_fork=%d\n",retorno_fork );
		printf( "Proceso padre, llamando a wait\n" );
		int retorno_wait = wait( &arg_wait );
		printf( "Proceso padre, despues de wait\n" );
		//%X, muestra en hexadecimal
		printf( "Proceso padre, el valor &arg_wait=%X\n",&arg_wait );
		printf( "Proceso padre, la variable arg_wait=%d\n",arg_wait );
		printf( "Proceso padre, la variable retorno_wait=%d\n",retorno_wait );
	}
	else{
		printf( "Proceso hijo, el pid es %d\n", getpid() );
	 	printf( "Proceso hijo, a dormir 10 seg\n" );
	 	sleep( 10 );
	 	printf( "Proceso hijo, despierta\n" );
	}
}