Executar o programa:

	Primeira opção (argumento -n)

		java -jar nome_do_ficheiro.jar -n ficheiro_media_nos.csv ficheiro_media_ramos.csv
	
		Este comando irá iniciar o programa na linha de comandos e consequentemente disponibilizar um menu para cada funcionalidade do mesmo

	Segunda opção(argumentos -t e -k)

		java -jar nome_do_ficheiro.jar -t -k x ficheiro_media_nos.csv ficheiro_media_ramos.csv

		Este comando irá correr o programa e escrever todos os resultados de cada uma das funcionalidades para um ficheiro, onde o caratér "x" é substituído pelo expoente na funcionalidade de calcular as potências da matriz de adjacências

Menu:
	
	Primeira opção (Grau dos nos):
		
		Esta opção do menu diz-nos o grau de cada no, ou seja, quantas ligações tem um no com os outros nos

	Segunda opção (Centralidade do vetor proprio):
	
		Esta opção indica-nos a popularidade de cada rede social analisada, sendo que o valor maior é a rede mais popular

	Terceira opção (Grau médio):

		Esta opção indica-nos a média de todos os graus dos nos

	Quarta opção (Densidade):

		Esta opção indica-nos a densidade da rede, ou seja, a relação entre o número de nos que existem e o número máximo que é possível existir

	Quinta opção (Potências da Matriz de Adjacências):
		
		Esta opção mostra-nos as x potências da matriz de adjacência

Ficheiro:

	O ficheiro de saída tem os resultados de todas as opções do menu e o nome dele é a data em que o programa foi executado, sendo que a data está no formato DDMMAAAA