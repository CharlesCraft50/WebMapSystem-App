document.addEventListener('DOMContentLoaded', function() {
  const toggleBtn = document.getElementById('toggle-container-btn');
  const searchContainer = document.getElementById('search-container');
  const searchBar = document.getElementById('search-bar');
  const residentList = document.getElementById('resident-listing');
  let currentBigHouseMarker = null;  // Variable to store the current BigHouse marker
  let bigHouseTimeout = null;  // Variable to store the timeout for BigHouse marker

  // Open/close the container when the floating button is clicked
  toggleBtn.addEventListener('click', function() {
    const isOpen = searchContainer.style.display === 'block';
    searchContainer.style.display = isOpen ? 'none' : 'block';
  });

  // Fetch all residents from your API (assuming it's hosted at '/api/residents')
  async function fetchResidents(query = '') {
    try {
      const response = await fetch(`/api/residents?query=${query}`);
      const data = await response.json();

      // Clear previous list
      residentList.innerHTML = '';

      // Add each resident to the list
      data.forEach(resident => {
        const li = document.createElement('li');
        li.textContent = `${resident.firstName} ${resident.lastName}`;
        li.dataset.residentId = resident.id; // Store resident ID in the list item
        li.addEventListener('click', function() {
          handleResidentClick(resident.id);
        });
        residentList.appendChild(li);
      });
    } catch (error) {
      console.error('Error fetching residents:', error);
    }
  }

  // Fetch residents when the page loads or the search bar is updated
  searchBar.addEventListener('input', function() {
    fetchResidents(searchBar.value);
  });

  // Handle the click on a resident and highlight the corresponding house on the map
  function handleResidentClick(residentId) {
    searchContainer.style.display = 'none';
    
    // Update API path to match backend endpoint
    fetch(`/api/residents/${residentId}/houses`)
        .then(response => {
            if (!response.ok) {
                if (response.status === 404) {
                    throw new Error('Resident not found or has no houses');
                }
                throw new Error('Failed to fetch houses');
            }
            return response.json();
        })
        .then(houses => {
            if (!houses || houses.length === 0) {
                console.log('No houses found for resident');
                return;
            }
            
            showBigHouseMarkers(houses);
        })
        .catch(error => {
            console.error('Error highlighting houses:', error);
        });
  }

  // Function to highlight the house when a resident is clicked
  function highlightHouseForResident(residentId) {
    fetch(`/api/residents/house/${residentId}`)
      .then(response => {
        if (!response.ok) {
          throw new Error('House not found or API error');
        }
        return response.json();
      })
      .then(houseData => {
        const { house } = houseData;
        if (house.lat && house.lng) {
          showBigHouseMarker(house);
        }
      })
      .catch(error => console.error('Error fetching house data:', error));
  }

  // Function to show the BigHouse marker with fade-in and fade-out animations
  function showBigHouseMarker(house) {
    // Clear existing timeout if any
    if (bigHouseTimeout) {
      clearTimeout(bigHouseTimeout);
    }

    // Remove existing marker if any
    if (currentBigHouseMarker) {
      currentBigHouseMarker.remove();
      currentBigHouseMarker = null;
    }

    // Create and add new BigHouse marker
    const houseMarker = L.marker([house.lat, house.lng], {
      icon: L.icon({
        iconUrl: '/image/house.png',
        iconSize: [30, 30],
        iconAnchor: [15, 30],
      }),
      interactive: false
    }).addTo(map);

    // Add fade-in animation
    houseMarker.getElement().style.transition = 'opacity 0.5s';
    houseMarker.getElement().style.opacity = '1';
    houseMarker.getElement().style.pointerEvents = 'none';

    // Store reference
    currentBigHouseMarker = houseMarker;

    // Set timeout to remove marker after 3 seconds
    bigHouseTimeout = setTimeout(() => {
      if (currentBigHouseMarker) {
        // Add fade-out animation
        currentBigHouseMarker.getElement().style.opacity = '0';
        setTimeout(() => {
          if (currentBigHouseMarker) {
            currentBigHouseMarker.remove();
            currentBigHouseMarker = null;
          }
        }, 500); // Wait for fade-out animation
      }
    }, 3000); // 3 seconds delay

    // Center map
    map.setView([house.lat, house.lng], 17);
  }

  function showBigHouseMarkers(houses) {
    // Clear existing markers
    if (currentBigHouseMarker) {
        currentBigHouseMarker.forEach(marker => marker.remove());
    }
    if (bigHouseTimeout) {
        clearTimeout(bigHouseTimeout);
    }

    currentBigHouseMarker = [];

    houses.forEach(house => {
        const marker = L.marker([house.lat, house.lng], {
            icon: L.icon({
                iconUrl: '/image/house.png',
                iconSize: [38, 38],
                iconAnchor: [21, 35],
            }),
            interactive: false
        }).addTo(map);

        marker.getElement().style.transition = 'opacity 0.5s';
        marker.getElement().style.opacity = '1';
        currentBigHouseMarker.push(marker);
    });

    // Center map on first house
    if (houses.length > 0) {
        map.setView([houses[0].lat, houses[0].lng], 17);
    }

    // Remove markers after 3 seconds
    bigHouseTimeout = setTimeout(() => {
        if (currentBigHouseMarker) {
            currentBigHouseMarker.forEach(marker => {
                marker.getElement().style.opacity = '0';
                setTimeout(() => marker.remove(), 500);
            });
            currentBigHouseMarker = null;
        }
    }, 3000);
  }

  // Initial fetch to load all residents
  fetchResidents();
});
