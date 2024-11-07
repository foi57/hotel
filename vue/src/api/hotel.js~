import request from '../request.js'

export default {
    addHotel: (form) => {
        console.log('addH',form)
        return request({
            url: '/addHotel',
            method: 'post',
            data: form
        })
    },
    deletePicture: (fileName) => {
        console.log('fileId',fileName)
        return request({
            url: '/deletePicture',
            method: 'post',
            params: {
                fileName
            }
        })
    },
    selectHotel: () => {
        return request({
            url: '/selectSpecialsHotels',
            method: 'get'
        })
    },
    selectHotelByCityTime: (city,timeStart,timeEnd,page,pageSize) => {
        return request({
            url: '/selectHotelByCityTime',
            method: 'post',
            params: {
                city,
                timeStart,
                timeEnd,
                page,
                pageSize
            }
        })
    },
    selectRoomByHotelIdTime: (hotelId,timeStart,timeEnd) => {
        return request({
            url: '/selectRoomByHotelIdTime',
            method: 'post',
            params: {
                hotelId,
                timeStart,
                timeEnd
            }
        })
    },

    selectHotelByUserId: (page,pageSize) => {
        return request({
            url: '/selectHotelByUserId',
            method: 'post',
            params: {
                page,pageSize
            }
        })
    },
    deleteHotelById: (id) => {
        return request({
            url: '/deleteHotelById',
            method: 'post',
            params: {
                id
            }
        })
    },
    updateHotel: (form) => {
        return request({
            url:"/updateHotel",
            method: "post",
            data: form
        })
}
}