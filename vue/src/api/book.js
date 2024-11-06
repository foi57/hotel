import request from '../request.js'

export default {
    booking: (bookInfo) => {
        console.log(bookInfo);
        return request({
            url: '/booking',
            method: 'post',
            data: bookInfo
        })
    },
    showBook: () => {
        return request({
            url: '/showBook',
            method: 'get'
        })
    },
    deleteBook: (bookId) => {
        return request({
            url: '/deleteBook',
            method: 'post',
            params: {
                bookId
            }
        })
    },
    UpdateStateBook: (bookId,state) => {
        return request({
            url: '/UpdateStateBook',
            method: 'post',
            params: {
                bookId,
                state
            }
        })
    }
}