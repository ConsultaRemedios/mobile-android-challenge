package com.benhurqs.checkout.data

import com.benhurqs.network.entities.Spotlight
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CartRepositoryTest{

    private lateinit var repository: CartRepository

    private var DISCOUNT: Double = 123.0
    private var PRICE: Double = 321.0
    private var TITLE  = "MOCK_TITLE"
    private var ID = 1
    private var IMAGE = "http://www.google"
    private var DESCRIPTION = "DESCRIPTION MOCK"
    private var PUBLISHER = "PUBLISHER MOCK"
    private var RATING = 5f
    private var STARS = 4f
    private var REVIEWS = 2
    private var QTD = 1


    @Before
    fun setUp(){
       initRepository()
    }

    @Test
    fun `check return null if list is empty`(){
        initRepository()
        Assert.assertTrue(repository.getList().isNullOrEmpty())
    }

    @Test
    fun `check return item correct`(){
        initRepository()
        var item = getMock()
        repository.addItem(item)

        Assert.assertFalse(repository.getList().isNullOrEmpty())
        var repositoryItem = repository.getList().get(0)

        Assert.assertEquals(item.id, repositoryItem.id)
        Assert.assertEquals(item.title, repositoryItem.title)
        Assert.assertEquals(item.publisher, repositoryItem.publisher)
        Assert.assertEquals(item.image, repositoryItem.image)
        Assert.assertTrue(item.discount == repositoryItem.discount)
        Assert.assertTrue(item.price == repositoryItem.price)
        Assert.assertEquals(item.description, repositoryItem.description)
        Assert.assertEquals(item.rating, repositoryItem.rating)
        Assert.assertEquals(item.stars, repositoryItem.stars)
        Assert.assertEquals(item.reviews, repositoryItem.reviews)
        Assert.assertEquals(1, repositoryItem.qtd)
    }

    @Test
    fun `check add qtd item correct`(){
        initRepository()
        val item = getMock()
        repository.addItem(item)

        Assert.assertFalse(repository.getList().isNullOrEmpty())
        Assert.assertTrue(repository.getList().size == 1)
        Assert.assertTrue(repository.getList().get(0).qtd == 1)

        repository.addItem(item)
        Assert.assertFalse(repository.getList().size == 2)
        Assert.assertTrue(repository.getList().get(0).qtd == 2)
    }

    @Test
    fun `check remove qtd item correct`(){
        initRepository()
        val item = getMock()
        repository.addItem(item)
        repository.addItem(item)

        Assert.assertFalse(repository.getList().size == 2)
        Assert.assertTrue(repository.getList().get(0).qtd == 2)

        repository.removeQtdItem(item)

        Assert.assertTrue(repository.getList().size == 1)
        Assert.assertTrue(repository.getList().get(0).qtd == 1)
    }

    @Test
    fun `check remove item correct`(){
        initRepository()
        val item = getMock()
        repository.addItem(item)

        Assert.assertTrue(repository.getList().size == 1)

        repository.removeItem(item)

        Assert.assertTrue(repository.getList().isNullOrEmpty())
    }

    @Test
    fun `check if add many items correct`(){
        initRepository()
        val item = getMock()
        repository.addItem(item.apply { id = 1 })
        repository.addItem(item.apply { id = 2 })
        repository.addItem(item.apply { id = 3 })

        Assert.assertTrue(repository.getList().size == 3)
    }


    @Test
    fun `check remove item when qtd = 0 correct`(){
        initRepository()
        val item = getMock()
        repository.addItem(item)

        Assert.assertTrue(repository.getList().size == 1)

        repository.removeQtdItem(item)

        Assert.assertTrue(repository.getList().isNullOrEmpty())
    }

    private fun initRepository(){
        repository = CartRepository()
        repository.clearCart()
    }

    private fun getMock() = Spotlight().apply {
            this.discount = DISCOUNT
            this.price = PRICE
            this.title = TITLE
            this.id = ID
            this.image = IMAGE
            this.description = DESCRIPTION
            this.publisher = PUBLISHER
            this.rating = RATING
            this.stars = STARS
            this.reviews = REVIEWS

    }
}