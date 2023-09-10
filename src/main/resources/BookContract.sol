// SPDX-License-Identifier: MIT
pragma solidity ^0.8.4;

import "@klaytn/contracts/KIP/token/KIP17/KIP17.sol";
import "@klaytn/contracts/KIP/token/KIP17/extensions/KIP17URIStorage.sol";
import "@klaytn/contracts/access/Ownable.sol";
import "@klaytn/contracts/utils/Counters.sol";

contract BookToken is KIP17, KIP17URIStorage, Ownable {

    using Counters for Counters.Counter;

    Counters.Counter private _tokenIdCounter;

    string public bookTitle;
    string public bookAuthor;
    uint256 public publicationYear;
    string public isbn;

    constructor(string memory name, 
                string memory symbol,
                string memory _bookTitle,
                string memory _bookAuthor,
                uint256 _publicationYear, 
                string memory _isbn) KIP17(name, symbol) {
        bookTitle = _bookTitle;
        bookAuthor = _bookAuthor;
        publicationYear = _publicationYear;
        isbn = _isbn;
    }

    function safeMint(address to, string memory uri) public onlyOwner {
        uint256 tokenId = _tokenIdCounter.current();
        _tokenIdCounter.increment();
        _safeMint(to, tokenId);
        _setTokenURI(tokenId, uri);
    }

    // The following functions are overrides required by Solidity.

    function _burn(uint256 tokenId) internal override(KIP17, KIP17URIStorage) {
        super._burn(tokenId);
    }

    function tokenURI(uint256 tokenId)
        public
        view
        override(KIP17, KIP17URIStorage)
        returns (string memory)
    {
        return super.tokenURI(tokenId);
    }
}
