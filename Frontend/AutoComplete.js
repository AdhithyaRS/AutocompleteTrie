import React, { useState } from 'react';

function Autocomplete() {
    const [query, setQuery] = useState('');
    const [results, setResults] = useState([]);
    const [selectedUser, setSelectedUser] = useState(null);

    const handleInputChange = async (e) => {
        const value = e.target.value;
        setQuery(value);
        if (value) {
            setSelectedUser({
                username: "",
                email: ""
            });
            // Adjusted the URL to match the backend endpoint:
            const response = await fetch(`http://localhost:8080/autocomplete/suggest?prefix=${value}`);
            const data = await response.json();
            if (data) {
                setResults(data);
            } else {
                setResults([]);
            }
        } else {
            setResults([]);
        }
    };

    const handleResultClick = (username, email) => {
        setSelectedUser({
            username: username,
            email: email
        });
        setQuery('');
        setResults({});
    };
    

    return (
        <div>
            <input
                type="text"
                placeholder="Start typing..."
                value={query}
                onChange={handleInputChange}
            />
            <ul>
                {Object.keys(results).map((username) => (
                    <li key={username} onClick={() =>handleResultClick(username, results[username])}>
                        {username}
                    </li>
                ))}
            </ul>
            {selectedUser && (
                <div>
                    <p>Username: {selectedUser.username}</p>
                    <p>Email: {selectedUser.email}</p>
                </div>
            )}
        </div>
    );
}

export default Autocomplete;
