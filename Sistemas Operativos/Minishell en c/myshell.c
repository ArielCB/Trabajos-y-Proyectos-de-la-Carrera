#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
#include <fcntl.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>
#include <signal.h>
#include "parser.h"

#define BUFFERSIZE 1024
typedef struct{
    int p[2];
}Pipes;
tline ProcesosBg[10];
int PidBG[100];
int cont=0;


void redirecciones(tline *linea){
    int file;
    if((*linea).redirect_input != NULL){
        file = open((*linea).redirect_input,O_RDONLY);
        if(file == -1){
            fprintf(stderr,"%s: Error. No existe el fichero.\n",(*linea).redirect_input);
            exit(1);
        }else{
            dup2(file,0);
        }
    }
    if((*linea).redirect_output != NULL){
        file = open((*linea).redirect_output,O_WRONLY | O_CREAT | O_TRUNC);
        if(file == -1){
            fprintf(stderr,"%s: Error. Fallo al crear el fichero para escritura.",(*linea).redirect_output);
            exit(1);
        }else{
            dup2(file,1);
        }
    }
    if((*linea).redirect_error != NULL){
        file = open((*linea).redirect_error, O_WRONLY | O_CREAT | O_TRUNC);
        if(file == -1){
            fprintf(stderr,"%s: Error. Fallo al crear el fichero para su escritura",(*linea).redirect_error);
            exit(1);
        }else{
            dup2(file,2);
        }
    }
}

void cd(tcommand *mandato){
    if((*mandato).argv[1]==NULL){
        chdir(getenv("HOME"));
    }
    else{
        chdir((*mandato).argv[1]);
    }
}

void jobs(){
    for(int i=0;i<cont;i++){
        char* concat="";
        tcommand *mandato = (ProcesosBg[i]).commands;
        for(int j=0;j<ProcesosBg[i].ncommands;j++){
            strcat(concat,(*mandato).argv[j]);
        }
        printf("[%d]  %s",i+1,concat);
    }
}

void fg(int id){
    id=id-1;
    tcsetpgrp(STDIN_FILENO, PidBG[id]);
    waitpid(PidBG[id],NULL,0);
    if(id!=cont){
        for(int i=id;i<cont;i++){
            PidBG[i]=PidBG[i+1];
        }
    }
}

void ejecutarMandato(tcommand *mandato){
    signal(SIGINT,SIG_DFL);
    signal(SIGQUIT,SIG_DFL);
    if(strcmp((*mandato).argv[0],"cd")==0){
        cd(mandato);
    }
    else if(strcmp((*mandato).argv[0],"jobs")==0){
        jobs();
    }
    else if(strcmp((*mandato).argv[0],"fg")==0){
        if((*mandato).argv[1]==NULL){
            fg(atoi((*mandato).argv[1]));
        }
        else{
            fg(cont);
        }
    }
    else{
        if((*mandato).filename==NULL){
            fprintf(stderr,"%s: No existe el mandato\n",(*mandato).argv[0]);
        }
        else{
            execvp((*mandato).argv[0],(*mandato).argv);
            fprintf(stderr,"%s: Error al ejecutar el mandato\n",(*mandato).filename);
            exit(1);
        }
    }
}

void variosMandatos(tline *linea){
    char* salida;
    tcommand *mandato = (*linea).commands;
    int mandatos = (*linea).ncommands;
    Pipes pipei[mandatos-1];
    pid_t pids [mandatos];
    for (int i = 0; i < mandatos-1; i++) {
        pipe(pipei[i].p);
    }
    signal(SIGQUIT, SIG_DFL);
    signal(SIGINT, SIG_DFL);
    for(int i = 0; i < mandatos; i++){
        pids[i] = fork();
        if(pids[i] < 0){
            fprintf(stderr,"Error al crear el fork.\n%s\n",strerror(errno));
            exit(1);
        }

        if(pids[i] == 0) {
            if (i == 0) {
                redirecciones(linea);
                close(pipei[i].p[0]);
                dup2(pipei[i].p[1], 1);
                if(strcmp(mandato[i].argv[0],"cd")==0){
                    cd(&mandato[i]);
                }
                else if(strcmp(mandato[i].argv[0],"jobs")==0){
                    jobs();
                }
                else if(strcmp(mandato[i].argv[0],"fg")==0){
                    if(mandato[i].argv[1]==NULL){
                        fg(atoi(mandato[i].argv[1]));
                    }
                    else{
                        fg(cont);
                    }
                }
                else{
                    if(mandato[i].filename==NULL){
                        fprintf(stderr,"%s: No existe el mandato\n",mandato[i].argv[0]);
                    }
                    else{
                        execvp(mandato[i].filename,mandato[i].argv);
                        fprintf(stderr,"%s: Error al ejecutar el mandato\n",mandato[i].filename);
                        exit(1);
                    }
                }
            } else{
                close(pipei[i - 1].p[1]);
                dup2(pipei[i - 1].p[0], 0);
                close(pipei[i].p[0]);
                dup2(pipei[i].p[1], 1);
                if(strcmp(mandato[i].argv[0],"cd")==0){
                    cd(&mandato[i]);
                }
                else if(strcmp(mandato[i].argv[0],"jobs")==0){
                    jobs();
                }
                else if(strcmp(mandato[i].argv[0],"fg")==0){
                    if(mandato[i].argv[1]==NULL){
                        fg(atoi(mandato[i].argv[1]));
                    }
                    else{
                        fg(cont);
                    }
                }
                else{
                    if(mandato[i].filename==NULL){
                        fprintf(stderr,"%s: No existe el mandato\n",mandato[i].argv[0]);
                    }
                    else{
                        execvp(mandato[i].filename,mandato[i].argv);
                        fprintf(stderr,"%s: Error al ejecutar el mandato\n",mandato[i].filename);
                        exit(1);
                    }
                }
            }
            exit(0);
        }
        signal(SIGQUIT, SIG_IGN);
        signal(SIGINT, SIG_IGN);
    }
    redirecciones(linea);
    close(pipei[mandatos-1].p[1]);
    read(pipei[mandatos-1].p[0],&salida,sizeof(char*));
    fprintf(stdout,"%s",salida);
    wait(NULL);
}

int main(){

    signal(SIGINT,SIG_IGN);
    signal(SIGQUIT, SIG_IGN);

	char buf[BUFFERSIZE];
    pid_t pid;

    printf("msh> ");

    while(fgets(buf,BUFFERSIZE,stdin)!=NULL){
        tline *linea = tokenize(buf);
        int SaveStdin = dup(0);
        int SaveStdout = dup(1);
        int SaveError = dup(2);
        redirecciones(linea);
        pid=fork();
        if(pid<0){
            fprintf(stderr,"Error con el fork.\n%s\n",strerror(errno));
        }
        else if(pid==0){
            if ((*linea).ncommands == 1){
                tcommand *mandato = (*linea).commands;
                ejecutarMandato(mandato);
            }
            else if((*linea).ncommands>1){
                variosMandatos(linea);
            }
        }
        else{
            if((*linea).background==0){
                wait(NULL);
            }
            else{
                ProcesosBg[cont]=*linea;
                PidBG[cont]=pid;
                cont++;
                fprintf(stdout,"[%d]\n",cont);
            }

        }
        signal(SIGINT,SIG_IGN);
        signal(SIGQUIT,SIG_IGN);
        dup2(SaveError,2);
        dup2(SaveStdin,0);
        dup2(SaveStdout,1);
        printf("msh> ");
    }
}
