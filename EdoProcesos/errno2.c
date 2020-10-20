#include <stdio.h>
#include <errno.h>

int main(){
	int result;
	
	//Guarda resultado del comando rename
	result = rename("this.txt","that.txt");
	if( result != 0 ){
		//Impresion de variable errno, error encontrado
		printf("errno:%d\n", errno);
		printf("File renaming error: ");
		
		//evaluar tipos de error devueltos por errno
		switch(errno){
			case EPERM:
		 		printf("Operation not permitted\n");
		   		break;
		 	case ENOENT:
		   		printf("File not found\n");
		        break;
		 	case EACCES:
		        printf("Permission denied\n");
		        break;
		 	case ENAMETOOLONG:
		        printf("Filename is too long\n");
		        break;
		 	default:
		        printf("Unknown error\n");
		}
		return(1);
  	}
  	puts("File renamed");

  	return(0);
}
