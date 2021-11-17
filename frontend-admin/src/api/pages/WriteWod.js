import axios from "axios";

const backEndBaseUri = process.env.REACT_APP_BACK_END_BASE_URI;

const insertWod = async (date, title, content) => {

    const data = {
        date: date,
        title: title,
        content: content,
    }

    const config = {
        method: 'POST',
        url: `${backEndBaseUri}/admin/api/wod`,
        data: data,
        withCredentials: true,
    }

    return await axios(config);
};

export { insertWod };