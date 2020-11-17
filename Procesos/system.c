#include <stdlib.h>
#include <stdio.h>

int main(){
	int return_value;
	//Hace llamada al sistema para ejecutar comando indicado
	//Similar a usar el comando desde terminal
	return_value = system("ls -l /");
	//Impresion del valor devuelto por system
	printf("Valor devuelto = %d\n", return_value);
	return return_value;
}
