#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

int main(){
	printf("ID real del usuario: %d\n", getuid());
	printf("ID efectivo del usuario: %d\n", geteuid());
	printf("ID real del grupo: %d\n", getgid());
	printf("ID efectivo del grupo: %d\n", getegid());
	return 0;
}
