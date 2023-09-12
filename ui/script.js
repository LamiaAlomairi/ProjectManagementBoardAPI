const host = window.location.host;

let url = 'http://localhost:8080/api/board';

let cardurl = 'http://localhost:8080/api/card';

// When the page loads, fetch all the boards from the server and display them
window.onload = function() {
  const selectOption = document.createElement('option');
  selectOption.textContent = 'Select board';
  selectOption.disabled = true;
  selectOption.selected = true;
  const boardsList = document.getElementById('boardSelect');
  boardsList.appendChild(selectOption);

  const boardSelect = document.getElementById('boardSelect');
  boardSelect.addEventListener('change', () => {
    selectedBoardId = boardSelect.value; // Save the selected board ID
    if (selectedBoardId) {
      fetchSectionsAndCardsForBoard(selectedBoardId);
    } else {
      const boardContainer = document.querySelector('.board-container');
      boardContainer.innerHTML = '';
    }
  });

  fetchBoards();
};
//______________________________________________________________________________________________________________
//Get all boards
function fetchBoards() {
  return fetch(url)
    .then(response => response.json())
    .then(data => {
      if (data.length === 0) {
        return createDefaultBoard().then(() => fetchBoards());
      } else {
        displayBoards(data);
        return data;
      }
    })
    .catch(error => {
      console.error('Error fetching boards:', error);
      return [];
    });
}
//______________________________________________________________________________________________________________
//Display all boards
function displayBoards(boards) {
  const boardsList = document.getElementById('boardSelect');
  boardsList.innerHTML = '';
  const selectOption = document.createElement('option');
  selectOption.textContent = 'Select board';
  selectOption.disabled = true;
  selectOption.selected = true;
  boardsList.appendChild(selectOption);

  boards.forEach(board => {
    const boardItem = document.createElement('option');
    boardItem.textContent = board.title;
    boardItem.value = board.id;
    boardsList.appendChild(boardItem);
  });
}
//______________________________________________________________________________________________________________
//Create defult board if database not has a board
async function createDefaultBoard() {
  const newBoard = {
    title: 'Sprint Board 2023'
  };

  try {
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(newBoard)
    });

    if (response.ok) {
      const responseText = await response.text();  // Read the response as plain text
      console.log('Response from server:', responseText);
      
      // Update the select option with the new board
      const boardSelect = document.getElementById('boardSelect');
      const newBoardOption = document.createElement('option');
      newBoardOption.textContent = newBoard.title;
      newBoardOption.value = responseText;  // Assuming responseText contains the new board ID
      boardSelect.appendChild(newBoardOption);

      console.log('Default board created.');
    } else {
      throw new Error('Board creation failed');
    }
  } catch (error) {
    console.error('Error creating default board:', error);
  }
}
//______________________________________________________________________________________________________________
// Function to create a new board
function createNewBoard() {
  const newBoardTitleInput = document.getElementById('newBoardTitleModal');
  const newBoardTitle = newBoardTitleInput.value;

  if (newBoardTitle.trim() === '') {
    alert('Please enter a valid board title.');
    return;
  }

  const newBoard = {
    title: newBoardTitle
  };

  fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(newBoard),
  })
    .then(response => response.json())  // Parse the response as JSON
    .then(createdBoard => {
      console.log('Response from server:', createdBoard);

      if (createdBoard.id) {  // Check if the response contains a valid board ID
        // Update the select option with the new board
        const boardSelect = document.getElementById('boardSelect');
        const newBoardOption = document.createElement('option');
        newBoardOption.textContent = newBoardTitle;
        newBoardOption.value = createdBoard.id;
        boardSelect.appendChild(newBoardOption);

        // Clear the input field and close the modal
        newBoardTitleInput.value = '';
        closeCreateBoardModal();

        alert('Board is created.');
      } else {
        console.error('Error creating board:', createdBoard);
      }
    })
    .catch(error => {
      console.error('Error creating board:', error);
    });
}
//______________________________________________________________________________________________________________
//Update title of board
function updateBoardTitle() {
  const newBoardTitleInput = document.getElementById('newBoardTitle');
  const newTitle = newBoardTitleInput.value;

  if (newTitle.trim() === '') {
    alert("Please enter a valid board title.");
    return;
  }

  fetch(url+`/${selectedBoardId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ title: newTitle }),
  })
    .then(response => response.json())
    .then(updatedBoard => {
      // Update the board title in the UI
      const boardSelect = document.getElementById('boardSelect');
      const selectedOption = boardSelect.querySelector(`option[value="${selectedBoardId}"]`);
      selectedOption.textContent = newTitle; // Update the text of the selected option

      newBoardTitleInput.value = ''; // Clear the input
    })
    .catch(error => {
      console.error('Error updating board title:', error);
    });
}
//______________________________________________________________________________________________________________
//Get all sections
// Event listener for when a board is selected from the dropdown
boardSelect.addEventListener('change', () => {
  selectedBoardId = boardSelect.value; // Save the selected board ID
  if (selectedBoardId) {
    fetchSectionsAndCardsForBoard(selectedBoardId);
  } else {
    const boardContainer = document.querySelector('.board-container');
    boardContainer.innerHTML = '';
  }
});

// Function to fetch sections and cards for a selected board
function fetchSectionsAndCardsForBoard(boardId) {
  fetch(url+`/${boardId}/section`)
    .then(response => response.json())
    .then(data => {
      // Assuming "data" is an array of sections, you can use it to render sections and cards
      renderSectionsAndCards(data);
    })
    .catch(error => {
      console.error('Error fetching sections and cards:', error);
    });
}
//______________________________________________________________________________________________________________
//Create section box
function renderSectionsAndCards(sections) {
  const boardContainer = document.querySelector('.board-container');
  boardContainer.innerHTML = '';

  sections.forEach(section => {
    const sectionContainer = document.createElement('div');
    sectionContainer.className = 'section';
    sectionContainer.setAttribute('data-id', section.id); // Add data-id attribute

    const sectionHeader = document.createElement('div');
    sectionHeader.className = 'section-header';

    const sectionTitle = document.createElement('div');
    sectionTitle.className = 'section-title';
    sectionTitle.textContent = section.name;

    const createCardBtn = document.createElement('div');
    createCardBtn.className = 'create-card-btn';
    createCardBtn.textContent = '+ Create Card';
    createCardBtn.addEventListener('click', () => createCard(section.id)); // Pass the section ID

    const cardsContainer = document.createElement('div');
    cardsContainer.className = 'cards-container';

    sectionHeader.appendChild(sectionTitle);
    sectionHeader.appendChild(createCardBtn);
    sectionContainer.appendChild(sectionHeader);
    sectionContainer.appendChild(cardsContainer);

    boardContainer.appendChild(sectionContainer);

    // Fetch and render cards for the current section and board 
    fetch(url+`/${selectedBoardId}/sections/${section.id}/cards`)
      .then(response => response.json())
      .then(cardsData => renderCardsInSection(cardsData, cardsContainer))
      .catch(error => {
        console.error('Error fetching cards:', error);
      });
  });
}
//______________________________________________________________________________________________________________
//Create card box
function renderCardsInSection(cardsData, cardsContainer) {
  cardsContainer.innerHTML = '';

  cardsData.forEach(card => {
    const cardContainer = document.createElement('div');
    cardContainer.className = 'card';

    const cardContent = document.createElement('div');
    cardContent.className = 'card-content';

    //For card id ***********************************
    const cardIdElement = document.createElement('div');
    cardIdElement.className = 'card-id';

    const idBold = document.createElement('span');
    idBold.textContent = '#';
    idBold.style.fontWeight = 'bold';

    const idText = document.createElement('span');
    idText.textContent = card.id;
    idText.style.fontWeight = 'bold';

    cardIdElement.appendChild(idBold);
    cardIdElement.appendChild(idText);

    //For card title ***********************************
    const cardTitleElement = document.createElement('div');
    cardTitleElement.className = 'card-title';

    const titleText = document.createElement('span');
    titleText.textContent = card.title;
    titleText.style.fontWeight = 'bold';

    cardTitleElement.appendChild(titleText);

    //For card description ***********************************
    const cardDescriptionElement = document.createElement('div');
    cardDescriptionElement.className = 'card-description';

    const descriptionBold = document.createElement('span');
    descriptionBold.textContent = 'Description: ';

    const descriptionText = document.createElement('span');
    descriptionText.textContent = card.description;

    cardDescriptionElement.appendChild(descriptionBold);
    cardDescriptionElement.appendChild(document.createElement('br'));
    cardDescriptionElement.appendChild(descriptionText);

    cardContent.appendChild(cardIdElement);
    cardContent.appendChild(cardTitleElement);
    cardContent.appendChild(cardDescriptionElement);

    const actionsContainer = document.createElement('div');
    actionsContainer.className = 'actions';

    const updateIcon = document.createElement('span');
    updateIcon.className = 'update-icon';
    updateIcon.innerHTML = '&#9998;'; // Pen icon
    updateIcon.onclick = () => showUpdateForm(card, cardContainer);

    const deleteIcon = document.createElement('span');
    deleteIcon.className = 'delete-icon';
    deleteIcon.innerHTML = '&times'; // Cross icon
    deleteIcon.onclick = () => deleteCard(card.id, cardContainer);

    actionsContainer.appendChild(updateIcon);
    actionsContainer.appendChild(deleteIcon);

    cardContainer.appendChild(cardContent);
    cardContainer.appendChild(actionsContainer);

    cardsContainer.appendChild(cardContainer);
  });
}
//______________________________________________________________________________________________________________
//button to show box create card 
function createCard(sectionId) {
  const section = document.querySelector(`.section[data-id="${sectionId}"]`);
  const existingCardForm = section.querySelector('.card-form');
  
  if (!existingCardForm) {
    const cardContainer = document.createElement('div');
    cardContainer.className = 'card';
  
    const cardContent = document.createElement('div');
    cardContent.className = 'card-content';
    const cardAdded = document.createElement('div');
    cardContent.className = 'card-add';
  
    const cardTitleInput = document.createElement('input');
    cardTitleInput.id = 'cardTitleInput';
    cardTitleInput.type = 'text';
    cardTitleInput.placeholder = 'Enter card title';
  
    const cardDescriptionInput = document.createElement('input');
    cardDescriptionInput.id = 'cardDescriptionInput';
    cardDescriptionInput.type = 'text';
    cardDescriptionInput.placeholder = 'Enter a description';
  
    const saveButton = document.createElement('button');
    saveButton.id = 'saveBtn';
    saveButton.textContent = 'Create';
    saveButton.addEventListener('click', () => saveCard(sectionId));
  
    const cancelButton = document.createElement('button');
    cancelButton.id = 'cancelBtn';
    cancelButton.textContent = 'Cancel';
    cancelButton.addEventListener('click', () => {
      cardContainer.remove();
    });
  
    cardAdded.appendChild(cardTitleInput);
    cardAdded.appendChild(cardDescriptionInput);
    cardAdded.appendChild(document.createElement('br')); // New line
    cardAdded.appendChild(saveButton);
    cardAdded.appendChild(cancelButton);
    cardContent.appendChild(cardAdded);
  
    cardContainer.appendChild(cardContent);
    section.appendChild(cardContainer);
  }
}
//______________________________________________________________________________________________________________
//Save card in database
function saveCard(sectionId) {
  const cardTitleInput = document.getElementById('cardTitleInput');
  const cardDescriptionInput = document.getElementById('cardDescriptionInput');

  const newCard = {
    title: cardTitleInput.value,
    description: cardDescriptionInput.value,
    sectionId: sectionId, // Pass the section ID directly
    boardId: selectedBoardId, // Include the selected board ID
  };

  fetch(cardurl, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(newCard),
  })
    .then(response => {
      if (response.ok) {
        return response.text();  // Read the response as plain text
      } else {
        throw new Error('Card creation failed');
      }
    })
    .then(responseText => {
      // The responseText contains the plain text response
      console.log('Response from server:', responseText);

      // Refresh sections/cards
      fetchSectionsAndCardsForBoard(selectedBoardId);

      // Clear the input fields
      cardTitleInput.value = '';
      cardDescriptionInput.value = '';
    })
    .catch(error => {
      console.error('Error creating card:', error);
    });
}
//______________________________________________________________________________________________________________
//Update card details 
function showUpdateForm(card, cardContainer) {
  const cardContent = cardContainer.querySelector('.card-content');

  // Save the original card content
  const originalCardContent = cardContent.innerHTML;

  // Clear existing content
  cardContent.innerHTML = '';

  const cardTitleInput = document.createElement('input');
  cardTitleInput.id = 'updateCardTitleInput';
  cardTitleInput.type = 'text';
  cardTitleInput.value = card.title;

  const cardDescriptionInput = document.createElement('input');
  cardDescriptionInput.id = 'updateCardDescriptionInput';
  cardDescriptionInput.type = 'text';
  cardDescriptionInput.value = card.description;

  const sectionSelect = document.createElement('select');
  sectionSelect.id = 'updateSectionSelect';

  // Function to fetch and populate sections in the dropdown
  function populateSectionDropdown() {
    // Clear existing options
    sectionSelect.innerHTML = '';

    fetch(url+`/${selectedBoardId}/section`)
      .then(response => response.json())
      .then(sections => {
        if (Array.isArray(sections)) {
          sections.forEach(section => {
            const option = document.createElement('option');
            option.value = section.id;
            option.textContent = section.name;
            sectionSelect.appendChild(option);
          });

          // Pre-select the current section if it exists
          if (card.section && card.section.id) {
            sectionSelect.value = card.section.id;
          }
        } else {
          console.error('Invalid sections response:', sections);
        }
      })
      .catch(error => {
        console.error('Error fetching sections:', error);
      });
  }

  // Call the function to populate the section dropdown
  populateSectionDropdown();

  const saveButton = document.createElement('button');
  saveButton.textContent = 'Save';
  saveButton.id = 'saveBtn';
  saveButton.addEventListener('click', () => {
    saveUpdatedCard(card.id);
    // Restore the original card content and display it
    cardContent.innerHTML = originalCardContent;
  });

  const cancelButton = document.createElement('button');
  cancelButton.textContent = 'Cancel';
  cancelButton.id = 'cancelBtn';
  cancelButton.addEventListener('click', () => {
    // Restore the original card content and display it
    cardContent.innerHTML = originalCardContent;
  });

  cardContent.appendChild(cardTitleInput);
  cardContent.appendChild(document.createElement('br')); // New line
  cardContent.appendChild(cardDescriptionInput);
  cardContent.appendChild(document.createElement('br')); // New line
  cardContent.appendChild(sectionSelect);
  cardContent.appendChild(document.createElement('br')); // New line
  cardContent.appendChild(saveButton);
  cardContent.appendChild(cancelButton);
}
//______________________________________________________________________________________________________________
//Save the update card details
function saveUpdatedCard(cardId) {
  const updatedTitleInput = document.getElementById('updateCardTitleInput');
  const updatedDescriptionInput = document.getElementById('updateCardDescriptionInput');
  const updatedSectionSelect = document.getElementById('updateSectionSelect');
  const updatedSectionId = updatedSectionSelect.value; // Get the updated section ID

  const updatedCard = {
    title: updatedTitleInput.value,
    description: updatedDescriptionInput.value,
    sectionId: updatedSectionId, // Include the updated section ID
  };

  fetch(cardurl+`/${cardId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(updatedCard),
  })
    .then(response => {
      if (response.ok) {
        return response.json();
      } else {
        throw new Error(`Error updating card: ${response.statusText}`);
      }
    })
    .then(updatedCardData => {
      fetchSectionsAndCardsForBoard(selectedBoardId); // Refresh sections/cards
    })
    .catch(error => {
      console.error('Error updating card:', error);
    });
}
//______________________________________________________________________________________________________________
//Delete card 
function deleteCard(cardId, cardContainer) {
  const shouldDelete = window.confirm("Are you sure you want to delete this card?");
  if (shouldDelete) {
    fetch(cardurl+`/${cardId}`, {
      method: 'DELETE',
    })
      .then(response => {
        if (response.ok) {
          cardContainer.remove(); // Remove the card container from the UI
        } else {
          console.error('Error deleting card:', response.statusText);
        }
      })
      .catch(error => {
        console.error('Error deleting card:', error);
      });
  }
}
//______________________________________________________________________________________________________________
//Open the Form Modal to Create Board 
function openCreateBoardModal() {
  const modal = document.getElementById('createBoardModal');
  modal.style.display = 'block';
}
//______________________________________________________________________________________________________________
//Close create board Modale
function closeCreateBoardModal() {
  const modal = document.getElementById('createBoardModal');
  modal.style.display = 'none';
  document.getElementById('newBoardTitleModal').value = ''; // Clear the input field in the modal
}

