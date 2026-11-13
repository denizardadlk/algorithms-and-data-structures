#include "RoutePlanner.h"
#include <iostream>
#include <fstream>
using namespace std;
// Array to help you out with name of the cities in order
const std::string cities[81] = {
    "Adana", "Adiyaman", "Afyon", "Agri", "Amasya", "Ankara", "Antalya", "Artvin", "Aydin", "Balikesir", "Bilecik",
    "Bingol", "Bitlis", "Bolu", "Burdur", "Bursa", "Canakkale", "Cankiri", "Corum", "Denizli", "Diyarbakir", "Edirne",
    "Elazig", "Erzincan", "Erzurum", "Eskisehir", "Gaziantep", "Giresun", "Gumushane", "Hakkari", "Hatay", "Isparta",
    "Mersin", "Istanbul", "Izmir", "Kars", "Kastamonu", "Kayseri", "Kirklareli", "Kirsehir", "Kocaeli", "Konya", "Kutahya",
    "Malatya", "Manisa", "Kaharamanmaras", "Mardin", "Mugla", "Mus", "Nevsehir", "Nigde", "Ordu", "Rize", "Sakarya",
    "Samsun", "Siirt", "Sinop", "Sivas", "Tekirdag", "Tokat", "Trabzon", "Tunceli", "Urfa", "Usak", "Van", "Yozgat",
    "Zonguldak", "Aksaray", "Bayburt", "Karaman", "Kirikkale", "Batman", "Sirnak", "Bartin", "Ardahan", "Igdir",
    "Yalova", "Karabuk", "Kilis", "Osmaniye", "Duzce"};

// Constructor to initialize and load constraints
RoutePlanner::RoutePlanner(const std::string &distance_data, const std::string &priority_data, const std::string &restricted_data, int maxDistance)
    : maxDistance(maxDistance), totalDistanceCovered(0), numPriorityProvinces(0), numWeatherRestrictedProvinces(0)
{

    // TO DO:
    // Load map data from file
    map.loadDistanceData(distance_data);
    // Mark all provinces as unvisited initially
    // Load priority provinces
    loadPriorityProvinces(priority_data);
    // Load restricted provinces
    loadWeatherRestrictedProvinces(restricted_data);
}

// Load priority provinces from txt file to an array of indices
void RoutePlanner::loadPriorityProvinces(const std::string &filename)
{
    // TODO: Your code here
    ifstream infile(filename);
    string line;
    int index = 0;
    while (getline(infile, line))
    {
        size_t start = line.find('(');
        size_t end = line.find(')');
        if (start != string::npos && end != string::npos)
        {
            string numberStr = line.substr(start + 1, end - start - 1);

            int number = stoi(numberStr);
            priorityProvinces[index] = number;
            index += 1;
            numPriorityProvinces++;
        }
    }

    infile.close();
}

// Load weather-restricted provinces from txt file to an array of indices
void RoutePlanner::loadWeatherRestrictedProvinces(const std::string &filename)
{
    // TODO: Your code here
    ifstream infile(filename);
    string line;
    int index = 0;
    while (getline(infile, line))
    {
        size_t start = line.find('(');
        size_t end = line.find(')');
        if (start != string::npos && end != string::npos)
        {
            string numberStr = line.substr(start + 1, end - start - 1);

            int number = stoi(numberStr);
            weatherRestrictedProvinces[index] = number;
            index += 1;
            numWeatherRestrictedProvinces++;
        }
    }
    infile.close();
}

// Checks if a province is a priority province
bool RoutePlanner::isPriorityProvince(int province) const
{
    // TODO: Your code here
    for (int i = 0; i < numPriorityProvinces; i++)
    {
        if (priorityProvinces[i] == province)
        {
            return true;
        }
    }
    return false;
}

// Checks if a province is weather-restricted
bool RoutePlanner::isWeatherRestricted(int province) const
{
    // TODO: Your code here
    for (int i = 0; i < numWeatherRestrictedProvinces; i++)
    {
        if (weatherRestrictedProvinces[i] == province)
        {
            return true;
        }
    }
    return false;
}

// Begins the route exploration from the starting point
void RoutePlanner::exploreRoute(int startingCity)
{
    // TODO: Your code here
    map.visited[startingCity] = true;
    stack.push(startingCity);
    route.push_back(startingCity);
    exploreFromProvince(startingCity);
}

// Helper function to explore from a specific province
void RoutePlanner::exploreFromProvince(int province)
{
    // TODO: Your code here
    int pro;
    enqueueNeighbors(province);
    while (!queue.isEmpty())
    {
        pro = queue.dequeue();

        if (isWeatherRestricted(pro))
        {
            cout << "Province " << cities[pro] << " is weather-restricted. Skipping.\n";
        }
        else if (map.isVisited(pro))
        {
            continue;
        }
        else
        {
            stack.push(pro);
            map.visited[pro] = true;

            route.push_back(pro);
            totalDistanceCovered += map.getDistance(province, pro);
            exploreFromProvince(pro);
        }
    }
    if (isExplorationComplete())
    {
        displayResults();
        exit(1);
    }
    else
    {
        backtrack();
        if (!stack.isEmpty())
        {
            exploreFromProvince(stack.peek());
        }
    }
}

void RoutePlanner::enqueueNeighbors(int province)
{
    // TO DO: Enqueue priority & non-priority neighbors to the queue according to given constraints
    for (size_t i = 0; i < MAX_QUEUE_SIZE; i++)
    {
        if (!map.isVisited(i) && map.isWithinRange(province, i, maxDistance))
        {
            if (isPriorityProvince(i))
            {
                queue.enqueuePriority(i);
            }
            else
            {
                queue.enqueue(i);
            }
        }
    }
}

void RoutePlanner::backtrack()
{
    // If you reach a dead-end province
    // TODO: Your code here
    int pro = stack.pop();
}

bool RoutePlanner::isExplorationComplete() const
{
    // TODO: Your code here
    if (stack.isEmpty() && queue.isEmpty())
    {
        return true;
    }

    return false;
}

void RoutePlanner::displayResults() const
{
    // TODO: Your code here
    // Display "Journey Completed!" message
    // Display the total number of provinces visited
    // Display the total distance covered
    // Display the route in the order visited
    // Priority Province Summary
    int count = map.countVisitedProvinces();
    cout << "----------------------------" << endl
         << "Journey Completed!" << endl
         << "----------------------------" << endl;
    cout << "Total Number of Provinces Visited: " << count << endl
         << "Total Distance Covered: " << totalDistanceCovered << " km" << endl;
    cout << "Route Taken:" << endl;
    for (size_t i = 0; i < count; i++)
    {
        cout << cities[route[i]] << " -> ";
    }
    int c = 0;
    cout << endl
         << "Priority Provinces Status:" << endl;
    for (size_t i = 0; i < numPriorityProvinces; i++)
    {
        cout << "- " << cities[priorityProvinces[i]] << " (";
        if (map.isVisited(priorityProvinces[i]))
        {
            cout << "Visited)" << endl;
            c += 1;
        }
        else
        {
            cout << "Not Visited)" << endl;
        }
    }

    cout << "Total Priority Provinces Visited: " << c << " out of " << numPriorityProvinces << endl;
    if (c != numPriorityProvinces)
    {
        cout << "Warning: Not all priority provinces were visited.";
    }
    else
    {
        cout << "Success: All priority provinces were visited.";
    }
}
