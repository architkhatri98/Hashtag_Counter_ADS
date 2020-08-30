import sys

def code_zero():
	print("\nYep, this is the one!!!")
	exit()

def code_one():
	print("\nBullshit code, try ANOTHER ONE... DJ Khaled")
	exit()

print(sys.argv)

f_sample = open(str(sys.argv[1]))
f_output = open(str(sys.argv[2]))

f_s = f_sample.readlines()
f_o = f_output.readlines()

l_s = []
l_o = []

print("\n\n")

for line in f_s:
	word = ''
	for char in line:
		if char == ',' or char == '\n':
			l_s.append(word)
			word = ''
		else:
			word = word + char
	

for line in f_o:
	word = ''
	for char in line:
		if char == ',' or char == '\n':
			l_o.append(word)
			word = ''
		else:
			word = word + char


l_s.sort()
l_o.sort()

print(l_s)
print("\n\n\n")
print(l_o)
print('\n\n')

primary_condition = (len(l_s) == len(l_o))
unmatching_indexes = []

bullshit = False
error_count = 0

if primary_condition:
	for i in range(len(l_s)):
		if l_s[i] != l_o[i]:
			error_count += 1
			print("ERROR! ", error_count, "\nExpected: ", l_s[i], " at " , i, " Got: ", l_o[i])
			bullshit = True
else:
	code_one()

if bullshit:
	code_one()

final_condition = (l_s == l_o)

if final_condition:
	code_zero()