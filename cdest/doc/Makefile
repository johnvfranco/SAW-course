FILE=scoreboard

pdf: ${FILE}.dvi
	dvipdf ${FILE}
	rm -f *.log *.aux *.dvi

${FILE}.dvi: ${FILE}.tex
	latex ${FILE}
	latex ${FILE}

clean:
	rm -f *.log *.aux *.dvi *.pdf
