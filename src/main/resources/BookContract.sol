// SPDX-License-Identifier: MIT
pragma solidity ^0.8.4;

import "@klaytn/contracts/KIP/token/KIP17/KIP17.sol";
import "@klaytn/contracts/KIP/token/KIP17/extensions/KIP17URIStorage.sol";
import "@klaytn/contracts/access/Ownable.sol";
import "@klaytn/contracts/utils/Counters.sol";
import "IAuthorContract.sol";

contract BookContract is KIP17, KIP17URIStorage, Ownable {

    IAuthorContract public authorContract;

    using Counters for Counters.Counter;

    Counters.Counter private _tokenIdCounter;

    constructor(address _authorContractAddress) KIP17("BookToken", "BTK") {
        authorContract = IAuthorContract(_authorContractAddress);
    }

    function safeMint(address to, string memory uri) public onlyOwner {
        require(authorContract.balanceOf(msg.sender) > 0, "Must own AuthorToken");
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
