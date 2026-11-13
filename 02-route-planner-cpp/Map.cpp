#include "Map.h"
#include <fstream>
#include <iostream>
#include <sstream>
#include <vector>
using namespace std;
Map::Map()
{
    // TODO: Your code here
    // Initialize all distances to a value representing no direct connection
    for (size_t i = 0; i < MAX_SIZE; i++)
    {
        for (size_t j = 0; j < MAX_SIZE; j++)
        {
            distanceMatrix[i][j]=-1;
        }
    }
    // Initialize all provinces as unvisited
    for (size_t i = 0; i < MAX_SIZE; i++)
    {
        visited[i]=false;
    }
    
}

// Loads distance data from a file and fills the distanceMatrix
void Map::loadDistanceData(const std::string &filename)
{

    // TODO: Your code here
    // Read each line in the CSV file
    // Read each cell separated by a comma
    // Convert cell to an integer and store in distanceMatrix
    vector<vector<int>> data;
    ifstream infile(filename);
    if (!infile)
    {
        cerr << "Error opening file: " << filename << endl;
        
    }
    string line;
    while (getline(infile, line))
    {
        stringstream ss(line);
        string item;
        vector<int> row;
        while (getline(ss, item, ','))
        {
            row.push_back(stoi(item));
        }
        data.push_back(row);
    }
    for (size_t i = 0; i < data.size(); i++)
    {
        for (size_t j = 0; j < data[0].size(); j++)
        {
            distanceMatrix[i][j]=data[i][j];
        }
    }
    infile.close();
    
}
// Checks if the distance between two provinces is within the allowed maxDistance
bool Map::isWithinRange(int provinceA, int provinceB, int maxDistance) const
{
    // TODO: Your code here
    if (distanceMatrix[provinceA][provinceB]<maxDistance)
    {
        return true;
    }
    
    return false;
}

// Marks a province as visited
void Map::markAsVisited(int province)
{
    visited[province]=true;
}

// Checks if a province has already been visited
bool Map::isVisited(int province) const
{
    // TODO: Your code here
    if (visited[province]==true)
    {
        return true;
    }
    
    return false;
}

// Resets all provinces to unvisited
void Map::resetVisited()
{
    // TODO: Your code here
    for (size_t i = 0; i < MAX_SIZE; i++)
    {
        visited[i]=false;
    }
    
}

// Function to count the number of visited provinces
int Map::countVisitedProvinces() const
{
    // TODO: Your code here
    int count=0;
    for (size_t i = 0; i < MAX_SIZE; i++)
    {
        if(visited[i]==true){
            count+=1;
        }
    }
    return count;
}

// Function to get the distance between two provinces
int Map::getDistance(int provinceA, int provinceB) const
{
    // TODO: Your code here
    return distanceMatrix[provinceA][provinceB];
}