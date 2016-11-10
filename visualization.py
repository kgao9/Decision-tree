# This is requirements gathering
# We first need to create the decision tree, and therefore
# python is the best tool for this - besides R, but I'm less familiar with R
# our data is in a csv and python has a specific package and a good GUI that deals with that

import pandas
import math

data = pandas.DataFrame.from_csv('train_house_votes_1984.csv')

# I could make this into one function, but then the logic's harder to see
# Easier to leave people confused
# This way, I enumerate all steps

#get column names except for their affiliation
column_names = list(data.columns.values)

#we want to caluculate aonditional entropy
#we'll be given a dataframe with all yes or all nos
# what we'll do is take that dataframe and calculate H(democrat or republican)
def h_y_given_x_equal_v(frame):
    numberOfPeople = len(frame.index)

    if(numberOfPeople == 0):
        return 0
    
    p1 = float(len((frame.loc[frame['p_aff'] == 'democrat']).index))/numberOfPeople
    p2 = float(len((frame.loc[frame['p_aff'] == 'republican']).index))/numberOfPeople

    if p1 == 0 or p2 == 0:
        return 0

    return -p1*math.log(p1,2) + -p2*math.log(p2,2)

def h_y_given_x(frame, index):
    numberOfPeople = len(frame.index)

    if(numberOfPeople == 0):
        return 0

    yes = frame.loc[frame[index] == 'y']
    no = frame.loc[frame[index] == 'n']

    prxY = float(len(yes.index)) / float(numberOfPeople)
    prxN = float(len(no.index))/float(numberOfPeople)

    h1 = h_y_given_x_equal_v(frame.loc[frame[index] == 'y'])
    h2 = h_y_given_x_equal_v(frame.loc[frame[index] == 'n'])
 
    if(h1 == 0 or h2 == 0 or prxY == 0 or prxN == 0):
        return 0 
 
    return prxY * h1 + prxN * h2

def h_y(frame):
    numberOfPeople = len(frame.index)

    print("initial")
    print(float(len((frame.loc[frame['p_aff'] == 'democrat']).index)))
    print(float(len((frame.loc[frame['p_aff'] == 'republican']).index)))
    print("end")

    if(numberOfPeople == 0):
        return 0

    pdemo = float(len((frame.loc[frame['p_aff'] == 'democrat']).index)) / float(numberOfPeople)
    prepub = float(len((frame.loc[frame['p_aff'] == 'republican']).index)) / float(numberOfPeople)

    if pdemo == 0 or prepub == 0:
        return 0

    return -pdemo * math.log(pdemo, 2) + -prepub * math.log(pdemo, 2)

column_names.remove('p_aff')

values = []

#with this data, let's find the first question
#for name in column_names:
#    print(name)
#    print(h_y(data) - h_y_given_x(data, name))

#we split on pff
# save root
root = data

#first node
node1 = root.loc[root['physician_fee_freeze'] == 'y']

node1_columns = column_names
node1_columns.remove('physician_fee_freeze')

#for name in node1_columns:
#    print(name)
#    print(h_y(node1) - h_y_given_x(node1, name))

# second node
node2 = node1.loc[node1['handicapped_infants'] == 'y']
node2_columns = node1_columns
node2_columns.remove('handicapped_infants')

#for name in node2_columns:
#    print(name)
#    print(h_y(node2) - h_y_given_x(node2, name))

#third node
node3 = node1.loc[node1['handicapped_infants'] == 'n']
node3_columns = node1_columns

#for name in node3_columns:
#    print(name)
#    print(h_y(node3) - h_y_given_x(node3, name))

#fourth node
node4 = node3.loc[node3['water_project_cost_sharing'] == 'y']
node4_columns = node3_columns
node4_columns.remove('water_project_cost_sharing')

#for name in node4_columns:
#    print(name)
#    print(h_y(node4) - h_y_given_x(node4, name))

node5 = node4.loc[node4['adoption_of_the_budget_resolution'] == 'y']
node5_columns = node4_columns
node5_columns.remove('adoption_of_the_budget_resolution')

#for name in node5_columns:
#    print(name)
#    print(h_y(node5) - h_y_given_x(node5, name))

node6 = node5.loc[node5['el_salvador_aid'] == 'y']
node6_columns = node5_columns
node6_columns.remove('el_salvador_aid')

#for name in node6_columns:
#    print(name)
#    print(h_y(node6) - h_y_given_x(node6, name))

#node7 = node6.loc[node6['religous_groups_in_school'] == 'y']
#node7_columns = node6_columns
#node7_columns.remove('religous_groups_in_school')

#for name in node7_columns:
#    print(name)
#    print(h_y(node7) - h_y_given_x(node7, name))

node8 = node6.loc[node6['anti_satellite_test_ban'] == 'y']
node8_columns = node6_columns
node8_columns.remove('anti_satellite_test_ban')

print node6

#for name in node8_columns:
#    print(name)
#    print(h_y(node8) - h_y_given_x(node8, name))
