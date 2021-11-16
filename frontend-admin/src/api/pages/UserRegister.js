import axios from "axios";

const backEndBaseUri = process.env.REACT_APP_BACK_END_BASE_URI;

const getBox = async (id) => {

    const config = {
        method: 'GET',
        url: `${backEndBaseUri}/admin/api/box`,
        withCredentials: true,
    }

    return await axios(config);
};

const getNotRegisteredUser = async (id) => {

    const config = {
        method: 'GET',
        url: `${backEndBaseUri}/admin/api/user/not-registered`,
        withCredentials: true,
    }

    return await axios(config);
};

const insertNewBoxToUser = async (boxId, email) => {

    const data = {
        boxId: boxId,
        email: email,
    }

    const config = {
        method: 'POST',
        url: `${backEndBaseUri}/admin/api/user/register`,
        data: data,
        withCredentials: true,
    }

    return await axios(config);
};

export { getBox, getNotRegisteredUser, insertNewBoxToUser };